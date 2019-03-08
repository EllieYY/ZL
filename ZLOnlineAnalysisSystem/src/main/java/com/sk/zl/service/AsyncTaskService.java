package com.sk.zl.service;

import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoEx;
import com.sk.zl.dao.meter.impl.MeterCodeDaoEx;
import com.sk.zl.dao.meter.impl.MeterRateDaoEx;
import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.entity.MeterCodeEntity;
import com.sk.zl.entity.MeterEntity;
import com.sk.zl.entity.MeterRateEntity;
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

    @Async
    public List<GenPowerEntity> meterCodeUpdate(List<MeterCode> meterCodes) {
        log.debug("#meterCodeUpdate# meter codes to update: " + meterCodes);
        List<GenPowerEntity> genPowerEntities = new ArrayList<GenPowerEntity>();
        for (MeterCode code : meterCodes) {
            int meterId = code.getMeterId();
            //#1 查找倍率
            double rate = meterRateDaoEx.findByMeterIdAndTime(meterId, code.getTime());
            if (Double.MAX_VALUE == rate) {
                log.debug("#meterCodeUpdate#No rate for meter#" + code.getMeterId() + ", time#" + code.getTime());
                continue;
            }

            //#2 计算电量 = code差值 * rate
            Date endDay = code.getTime();
            endDay = DateUtil.dateTimeToDate(endDay);
            Date preDay = DateUtil.dateAddDays(endDay, -1, false);

            //# 若一天录入多个表码值，取时间最大的
            List<MeterCodeEntity> codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                    meterId, preDay, endDay);
            if (codeEntities.size() < 1) {
                log.debug("#meterCodeUpdate#No preDay code for meter#" + code.getMeterId() + ", time#" + code.getTime());
                continue;
            }

            double preCode = codeEntities.get(0).getCode();
            double curCode = code.getCode();
            double genPower = rate * (curCode - preCode);

            //#3 存储电量
            GenPowerEntity power = genPowerDaoEx.saveOne(genPower, code.getTime(), meterId);
            genPowerEntities.add(power);

            log.debug("#meterCodeUpdate#update meter code ok: meterId#" + code.getMeterId() + ", time#" + code.getTime() + "\n" +
                    "curCode#" + curCode + ", preCode#" + preCode + ", rate#" + rate + "\n" +
                    "update power: " + power);
        }

        return genPowerEntities;
    }

    @Async
    public void metreRateUpdate(int meterId, MeterRate rate) {
        log.debug("#metreRateUpdate#meter rate to update: meterId#" + meterId + ", rate#" + rate);

        List<DateSection> updateDate = new ArrayList<DateSection>();
        DateSection rateSection = new DateSection(rate.getStartTime(), rate.getEndTime());

        //#1 判断更新类型
        if (rate.getId() != 0) {
            MeterRateEntity oldRate = meterRateDaoEx.findById(rate.getId());
            if (oldRate.getRate() == rate.getRate()) {
                updateDate = DateSection.differenceSet(
                        rateSection,
                        new DateSection(oldRate.getStartTime(), oldRate.getEndTime()));
            }
            meterRateDaoEx.updateById(
                    rate.getId(),
                    rate.getRate(),
                    rate.getStartTime(),
                    rate.getEndTime());

            log.debug("#metreRateUpdate#old rate: " + oldRate);

        } else {
            updateDate.add(rateSection);
            MeterEntity meter = meterDao.getOne(meterId);
            meterRateDaoEx.save(rate.toEntity(meter));
        }

        log.debug("#metreRateUpdate#date section to update: " + updateDate);

        //#2 电量回算
        int size = updateDate.size();
        for (int i = 0; i < size; i++) {
            calculatePower(updateDate.get(i), meterId, rate.getRate());
        }
    }

    private void calculatePower(DateSection updateDate, int meterId, double rate) {
        log.debug("#calculatePower# updateSection:" + updateDate);

        Date curDay = updateDate.getStart();
        curDay = DateUtil.dateTimeToDate(curDay);
        Date preDay = DateUtil.dateAddDays(curDay, -1, false);
        while (curDay.before(updateDate.getEnd())) {
            Date nextDay = DateUtil.dateAddDays(curDay, 1, false);

            // 前一天表码
            //# 若一天录入多个表码值，取时间最大的
            List<MeterCodeEntity> codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                    meterId, preDay, curDay);
            if (codeEntities.size() < 1) {
                preDay = curDay;
                curDay = nextDay;

                log.debug("#calculatePower# no code for meterId#" + meterId + " at " + preDay);
                continue;
            }
            double preCode = codeEntities.get(0).getCode();

            // 当天表码
            codeEntities = meterCodeDaoEx.findByMeterAndTimeDec(
                    meterId, curDay, nextDay);
            if (codeEntities.size() < 1) {
                preDay = curDay;
                curDay = nextDay;
                log.debug("#calculatePower# no code for meterId#" + meterId + " at " + curDay);
                continue;
            }
            double curCode = codeEntities.get(0).getCode();
            double genPower = (curCode - preCode) * rate;

            //#3 存储电量
            GenPowerEntity power = genPowerDaoEx.saveOne(genPower, curDay, meterId);

            log.debug("#calculatePower# calcuate power: meterId#" + meterId + ", time#" + curDay + "\n" +
                    "curCode#" + curCode + ", preCode#" + preCode + ", rate#" + rate + "\n" +
                    "update power: " + power);
        }
    }
}
