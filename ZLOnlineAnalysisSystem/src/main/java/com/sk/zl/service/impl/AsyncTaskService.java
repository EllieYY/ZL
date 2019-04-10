package com.sk.zl.service.impl;

import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.MeterNodeDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoEx;
import com.sk.zl.dao.meter.impl.MeterCodeDaoEx;
import com.sk.zl.dao.meter.impl.MeterRateDaoEx;
import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.MeterNodeEntity;
import com.sk.zl.entity.zheling.MeterRateEntity;
import com.sk.zl.model.meter.DateSection;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description : 异步任务执行类
 * @Author : Ellie
 * @Date : 2019/3/6
 */
@Service
public class AsyncTaskService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    MeterRateDaoEx meterRateDaoEx;
    @Resource
    GenPowerDaoEx genPowerDaoEx;
    @Resource
    MeterCodeDaoEx meterCodeDaoEx;
    @Resource
    MeterDao meterDao;
    @Resource
    MeterNodeDao meterNodeDao;

    @Async
    public List<GenPowerEntity> meterCodeUpdate(List<MeterCode> meterCodes) {
        log.debug("#meterCodeUpdate# meter codes to update: " + meterCodes);
        List<GenPowerEntity> genPowerEntities = new ArrayList<GenPowerEntity>();
        for (MeterCode code : meterCodes) {
            int meterNodeId = code.getMeterNodeId();

            //#2 对前后天的电量进行回算
            Date codeDay = code.getTime();
            codeDay = DateUtil.dateTimeToDate(codeDay);
            Date start = DateUtil.dateAddDays(codeDay, -1, false);
            Date end = DateUtil.dateAddDays(codeDay, 2, false);
            DateSection updateDate = new DateSection(start, end);
            calculatePower(updateDate, meterNodeId, 0.0, false);
        }

        return genPowerEntities;
    }


    @Async
    public void metreRateUpdate(List<DateSection> updateDate, int meterId, MeterRate rate) {
        MeterEntity meter = meterDao.findById(meterId).get();
        List<MeterNodeEntity> nodes = meter.getNodeSet();

        //#2 电量回算
        for (MeterNodeEntity node : nodes) {
            int nodeId = node.getId();
            int size = updateDate.size();
            for (int i = 0; i < size; i++) {
                calculatePower(updateDate.get(i), nodeId, rate.getRate(), true);
            }
        }
    }

    private void calculatePower(DateSection updateDate, int meterNodeId, double initRate, boolean hasRate) {
        double rate = initRate;

        //#1 根据meterNode查找meter，获取倍率信息。
        MeterNodeEntity meterNode = meterNodeDao.findById(meterNodeId).get();
        int meterId = meterNode.getMeter().getId();
        // 节点弃用，不参与回算
        int nodeType = meterNode.getType();
        if (nodeType == 5) {
            return;
        }

        Date curDay = updateDate.getStart();
        curDay = DateUtil.dateTimeToDate(curDay);
        Date preDay = DateUtil.dateAddDays(curDay, -1, false);
        while (curDay.before(updateDate.getEnd())) {
            // get rate
            if (!hasRate) {
                rate = meterRateDaoEx.findByMeterIdAndTime(meterId, curDay);
                if (Double.MAX_VALUE == rate) {
                    continue;
                }
            }
            Date nextDay = DateUtil.dateAddDays(curDay, 1, false);

            // 前一天表码
            //# 若一天录入多个表码值，取时间最大的
            List<MeterCodeEntity> codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                    meterNodeId, preDay, curDay);
            if (codeEntities.size() < 1) {
                preDay = curDay;
                curDay = nextDay;

                log.debug("#calculatePower# no code for meterNodeId#" + meterNodeId + " at " + preDay);
                continue;
            }
            double preCode = codeEntities.get(0).getCode();

            // 当天表码
            codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                    meterNodeId, curDay, nextDay);
            if (codeEntities.size() < 1) {
                preDay = curDay;
                curDay = nextDay;
                log.debug("#calculatePower# no code for meterNodeId#" + meterNodeId + " at " + curDay);
                continue;
            }
            double curCode = codeEntities.get(0).getCode();
            double genPower = (curCode - preCode) * rate;

            // 根据正向有功和反向有功类型调整电量数值。 0-正向有功， 1-反向有功
            genPower = (nodeType == 0 ? genPower : -genPower);

            //#3 存储电量
            GenPowerEntity power = genPowerDaoEx.saveOne(genPower, curDay, meterNodeId);

            log.debug("#calculatePower# calcuate power: meterNodeId#" + meterNodeId + ", time#" + curDay + "\n" +
                    "curCode#" + curCode + ", preCode#" + preCode + ", rate#" + rate + "\n" +
                    "update power: " + power);

            preDay = curDay;
            curDay = nextDay;
        }
    }
}
