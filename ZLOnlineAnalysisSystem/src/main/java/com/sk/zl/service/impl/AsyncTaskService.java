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
            //#1 根据meterNode查找meter，获取倍率信息。
            MeterNodeEntity meterNode = meterNodeDao.findById(meterNodeId).get();
            // 节点弃用，不参与回算
            int nodeType = meterNode.getType();
            if (nodeType == 5) {
                continue;
            }

            int meterId = meterNode.getMeter().getId();

            //#2 查找倍率
            double rate = meterRateDaoEx.findByMeterIdAndTime(meterId, code.getTime());
            if (Double.MAX_VALUE == rate) {
                log.debug("#meterCodeUpdate#No rate for meterNode#" + code.getMeterNodeId() + ", time#" + code.getTime());
                continue;
            }

            //#3 计算电量 = code差值 * rate
            Date endDay = code.getTime();
            endDay = DateUtil.dateTimeToDate(endDay);
            Date preDay = DateUtil.dateAddDays(endDay, -1, false);

            //# 若一天录入多个表码值，取时间最大的
            List<MeterCodeEntity> codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                    meterNodeId, preDay, endDay);
            if (codeEntities.size() < 1) {
                log.debug("#meterCodeUpdate#No preDay code for meterNode#" + code.getMeterNodeId() + ", time#" + code.getTime());
                continue;
            }

            double preCode = codeEntities.get(0).getCode();
            double curCode = code.getCode();
            double genPower = rate * (curCode - preCode);
            // 根据正向有功和反向有功类型调整电量数值。 0-正向有功， 1-反向有功
            genPower = meterNode.getType() == 0 ? genPower : -genPower;

            //#3 存储电量
            GenPowerEntity power = genPowerDaoEx.saveOne(genPower, code.getTime(), meterNodeId);
            genPowerEntities.add(power);

            log.debug("#meterCodeUpdate#update meter code ok: meterId#" + code.getMeterNodeId() + ", time#" + code.getTime() + "\n" +
                    "curCode#" + curCode + ", preCode#" + preCode + ", rate#" + rate + "\n" +
                    "update power: " + power);
        }

        return genPowerEntities;
    }

    @Async
    public void metreRateUpdate(List<DateSection> updateDate, int meterId, MeterRate rate) {
        //#2 电量回算
        int size = updateDate.size();
        for (int i = 0; i < size; i++) {
            calculatePower(updateDate.get(i), meterId, rate.getRate());
        }
    }

//    @Async
//    public MeterRate metreRateUpdate(int meterId, MeterRate rate) {
//        log.debug("#metreRateUpdate#meter rate to update: meterId#" + meterId + ", rate#" + rate);
//
//        List<DateSection> updateDate = new ArrayList<DateSection>();
//        DateSection rateSection = new DateSection(rate.getStartTime(), rate.getEndTime());
//
//        //#1 判断更新类型
////        if (rate.getId() != 0) {
//////            MeterRateEntity oldRate = meterRateDaoEx.findById(rate.getId());
//////            if (oldRate.getRate() == rate.getRate()) {
//////                updateDate = DateSection.differenceSet(
//////                        rateSection,
//////                        new DateSection(oldRate.getStartTime(), oldRate.getEndTime()));
//////            } else {
//////                updateDate.add(rateSection);
//////            }
////            updateDate.add(rateSection);
////            meterRateDaoEx.updateById(
////                    rate.getId(),
////                    rate.getRate(),
////                    rate.getStartTime(),
////                    rate.getEndTime());
////
//////            log.debug("#metreRateUpdate#old rate: " + oldRate);
////
////        } else {
//            updateDate.add(rateSection);
//            MeterEntity meter = meterDao.getOne(meterId);
//            MeterRateEntity meterRateEntity = rate.toEntity(meter);
//            meterRateDaoEx.save(meterRateEntity);
//            rate = MeterRate.fromEntity(meterRateEntity);
////        }
//
//        log.debug("#metreRateUpdate#date section to update: " + updateDate);
//
//        //#2 电量回算
//        int size = updateDate.size();
//        for (int i = 0; i < size; i++) {
//            calculatePower(updateDate.get(i), meterId, rate.getRate());
//        }
//
//        System.out.println("aysn: " + rate);
//        return rate;
//    }


    private void calculatePower(DateSection updateDate, int meterId, double rate) {
        log.debug("#calculatePower# updateSection:" + updateDate);

        // 查找meter电表下所有子节点
        MeterEntity meter = meterDao.findById(meterId).get();
        List<MeterNodeEntity> nodes = meter.getNodeSet();
        for (MeterNodeEntity node : nodes) {
            // 节点暂未使用
            int nodeType = node.getType();
            if (nodeType == 5) {
                continue;
            }

            int nodeId = node.getId();

            Date curDay = updateDate.getStart();
            curDay = DateUtil.dateTimeToDate(curDay);
            Date preDay = DateUtil.dateAddDays(curDay, -1, false);
            while (curDay.before(updateDate.getEnd())) {
                Date nextDay = DateUtil.dateAddDays(curDay, 1, false);

                // 前一天表码
                //# 若一天录入多个表码值，取时间最大的
                List<MeterCodeEntity> codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                        nodeId, preDay, curDay);
                if (codeEntities.size() < 1) {
                    preDay = curDay;
                    curDay = nextDay;

                    log.debug("#calculatePower# no code for meterNodeId#" + nodeId + " at " + preDay);
                    continue;
                }
                double preCode = codeEntities.get(0).getCode();

                // 当天表码
                codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                        nodeId, curDay, nextDay);
                if (codeEntities.size() < 1) {
                    preDay = curDay;
                    curDay = nextDay;
                    log.debug("#calculatePower# no code for meterNodeId#" + nodeId + " at " + curDay);
                    continue;
                }
                double curCode = codeEntities.get(0).getCode();
                double genPower = (curCode - preCode) * rate;

                // 根据正向有功和反向有功类型调整电量数值。 0-正向有功， 1-反向有功
                genPower = (nodeType == 0 ? genPower : -genPower);

                //#3 存储电量
                GenPowerEntity power = genPowerDaoEx.saveOne(genPower, curDay, nodeId);

                log.debug("#calculatePower# calcuate power: meterNodeId#" + nodeId + ", time#" + curDay + "\n" +
                        "curCode#" + curCode + ", preCode#" + preCode + ", rate#" + rate + "\n" +
                        "update power: " + power);

                preDay = curDay;
                curDay = nextDay;
            }
        }
    }
}
