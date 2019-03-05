package com.sk.zl.service.impl;

import com.sk.zl.aop.excption.DataDoException;
import com.sk.zl.aop.excption.ServiceException;
import com.sk.zl.config.StationConfig;
import com.sk.zl.dao.meter.GenPowerDao;
import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.MeterGroupDao;
import com.sk.zl.dao.meter.MeterRateDao;
import com.sk.zl.dao.meter.PlanPowerDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoExtension;
import com.sk.zl.dao.setting.LoginLogDao;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.entity.LoginLogEntity;
import com.sk.zl.entity.MeterEntity;
import com.sk.zl.entity.MeterGroupEntity;
import com.sk.zl.entity.MeterRateEntity;
import com.sk.zl.entity.PlanPowerEntity;
import com.sk.zl.model.meter.Meter;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.request.ReTimeSlots;
import com.sk.zl.model.result.ResultBean;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.model.station.AnnualCapacityInfo;
import com.sk.zl.model.station.HydrologicalInfo;
import com.sk.zl.model.station.PowerStationSnapshot;
import com.sk.zl.model.station.StationAlarmNum;
import com.sk.zl.utils.DateUtil;
import com.sk.zl.utils.ResultBeanUtil;
import com.sk.zl.model.station.LoginLog;
import com.sk.zl.model.station.PlanPower;
import com.sk.zl.service.StationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Description : 发电厂信息服务
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Service
public class StationServiceImpl implements StationService {
    @Resource
    LoginLogDao loginLogDao;
    @Resource
    MeterDao meterDao;
    @Resource
    MeterRateDao meterRateDao;
    @Resource
    PlanPowerDao planPowerDao;
    @Resource
    PointInfoDao pointInfoDao;
    @Resource
    GenPowerDao genPowerDao;
    @Resource
    GenPowerDaoExtension genPowerDaoExtension;
    @Resource
    MeterGroupDao meterGroupDao;
    @Resource
    StationConfig stationConfig;

    @Override
    public int addLog(LoginLog log)  {
        loginLogDao.save(log.toEntity());
        return 0;
    }

    @Override
    public List<LoginLog> getLog(LoginLog log) {
        String group = log.getGroup();
        String user = log.getUser();
        Date startTime = log.getLoginTime();
        Date endTime = log.getLogoutTime();

        boolean isByGroup = !log.getGroup().equals("0");
        boolean isByUser = !log.getUser().equals("0");

        List<LoginLogEntity> entities;
        if (isByGroup && isByUser) {
            entities = loginLogDao.findByGroupAndUserAndLoginTimeIsBetween(group, user, startTime, endTime);
        } else if (isByGroup && !isByUser) {
            entities = loginLogDao.findByGroupAndLoginTimeIsBetween(group, startTime, endTime);
        } else if (!isByGroup && isByUser) {
            entities = loginLogDao.findByUserAndLoginTimeIsBetween(user, startTime, endTime);
        } else {
            entities = loginLogDao.findByLoginTimeIsBetween(startTime, endTime);
        }

        List<LoginLog> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(LoginLog.fromEntity(item));
        }, ArrayList::addAll);
        return models;
    }

    @Override
    public List<Meter> getMeterInfo()  {
        List<MeterEntity> entities = meterDao.findAll();
        List<Meter> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(Meter.fromEntity(item));
        }, ArrayList::addAll);
        return models;
    }

    @Override
    public List<MeterRate> getMeterRate(int meterId)  {
        List<MeterRateEntity> entities = meterRateDao.findByMeterId(meterId);
        List<MeterRate> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(MeterRate.fromEntity(item));
        }, ArrayList::addAll);

        return models;
    }

    @Override
    public List<MeterRate> addMeterRate(int meterId, List<MeterRate> models)  {
        MeterEntity meter = meterDao.getOne(meterId);
        if (null == meter) {
            throw new DataDoException("电表不存在: meterid = " + meterId);
        }

        List<MeterRateEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity(meter));
        }, ArrayList::addAll);

        meterRateDao.saveAll(entities);
        return models;
    }

    @Override
    public List<MeterRate> updateMeterRate(List<MeterRate> meterRaters) {
        for (MeterRate meterRate: meterRaters) {
            meterRateDao.updateRateById(
                    meterRate.getId(),
                    meterRate.getRate(),
                    meterRate.getStartTime(),
                    meterRate.getEndTime());
        }

        return meterRaters;
    }

    @Override
    public List<MeterRate> deleteMeterRate(List<MeterRate> models)  {
        List<MeterRateEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);

        meterRateDao.deleteInBatch(entities);
        return null;
    }

    @Override
    public List<PlanPower> getPlanPower()  {
        List<PlanPowerEntity> entities = planPowerDao.findAll();
        List<PlanPower> planPowers = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(PlanPower.fromEntity(item));
        }, ArrayList::addAll);

        return planPowers;
    }

    @Override
    public List<PlanPower> addPlanPowers(List<PlanPower> models)  {
        // 进行年份去重
        Iterator<PlanPower> it = models.iterator();
        while(it.hasNext()){
            PlanPower planPower = it.next();
            int size = planPowerDao.findByYear(planPower.getYear()).size();
            if(size > 0){
                it.remove();
            }
        }

        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.saveAll(entities);
        return models;
    }

    @Override
    public List<PlanPower> updatePlanPowers(List<PlanPower> models)  {
        for (PlanPower planPower:models) {
            planPowerDao.updatePlanPowerById(planPower.getId(), planPower.getPlanPower());
        }
        return models;
    }

    @Override
    public List<PlanPower> deletePlanPowers(List<PlanPower> models)  {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.deleteInBatch(entities);
        return models;
    }

    @Override
    public StationAlarmNum getAlarmNum()  {
        List<PointInfo> points = pointInfoDao.findStationAlarmPoints();

        if (points.size() != 2) {
            throw new RuntimeException("未找到报警点。");
        }

        StationAlarmNum alarmNum = new StationAlarmNum();
        alarmNum.setNumOfAccident(new Double(points.get(0).getValue()).intValue());
        alarmNum.setNumOfGlitches(new Double(points.get(1).getValue()).intValue());
        return alarmNum;
    }

    @Override
    public HydrologicalInfo getHydrologicalInfo()  {
        // TODO:水情系统尚未接入，暂时使用测试数据
        HydrologicalInfo info = new HydrologicalInfo();
        info.setWaterHead(42.56);
        info.setInflow(7.31);
        info.setOutflow(7.31);
        info.setUpstreamWaterLevel(42.56);
        info.setDownstreamWaterLevel(20.56);
        return info;
    }

    @Override
    public PowerStationSnapshot getStationSnapshot()  {
        PowerStationSnapshot stationSnapshot = new PowerStationSnapshot();
        try {
            // 安全生产天数 = 当前日期 - 2005/9/10
            int totalSafetyDays = DateUtil.daysBetweenDate(DateUtil.getNow(), "2005-09-10");

            // 年安全生产天数 = 当前日期在本年中的天数
            int annualSafetyDays = DateUtil.daysOfTodayInYear();

            // 全厂实时总有功：rest取点统计
            double realTimeActivePower = 0;
            List<PointInfo> realTimeActivePowerPoints = pointInfoDao.findRealTimeActivePowerPoints();

            for (PointInfo point : realTimeActivePowerPoints) {
                realTimeActivePower += point.getValue();
            }

            int genGroupId = stationConfig.getGenCapacityMeterGroup();
            Date today = new Date();
            // 昨日发电量
            Date yesterday = DateUtil.dateAddDays(today, -1, false);
            double preDayGenCapacity = calculateGenPowerByGroup(genGroupId, yesterday, today);

            // 年累计发电量
            Date beginYear = DateUtil.getFirstDateOfYear(today);
            beginYear = DateUtil.dateTimeToDate(beginYear);
            double annualGenCapacity = calculateGenPowerByGroup(genGroupId, beginYear, today);

            // 年累计上网电量
            int ongridGroupId = stationConfig.getOnGridMeterGroup();
            double annualOnGridPower = calculateGenPowerByGroup(ongridGroupId, beginYear, today);

            stationSnapshot.setTotalSafetyDays(totalSafetyDays);
            stationSnapshot.setAnnualSafetyDays(annualSafetyDays);
            stationSnapshot.setRealTimeActivePower(realTimeActivePower);
            stationSnapshot.setPreDayGenCapacity(preDayGenCapacity);
            stationSnapshot.setAnnualGenCapacity(annualGenCapacity);
            stationSnapshot.setAnnualOnGridPower(annualOnGridPower);
        }catch (Exception e) {
            throw new ServiceException(e.toString());
        }

        return stationSnapshot;
    }

    private double calculateGenPowerByGroup(int groupId, Date startTime, Date endTime) {
        MeterGroupEntity group = meterGroupDao.findById(groupId);
        List<Integer> metersId = new ArrayList<>();
        for (MeterEntity meter: group.getMeterSet()) {
            metersId.add(meter.getId());
        }
//        List<GenPowerEntity> results = genPowerDao.findByMeterIdInAndTimeGreaterThanEqualAndTimeLessThan(metersId, startTime, endTime);
        List<GenPowerEntity> results = genPowerDaoExtension.findByMeterIdAndTime(metersId, startTime, endTime);

        System.out.println("表ID：" + metersId);
        System.out.println("start:" + startTime + ", end: " + endTime);
        System.out.println("电量信息：" + results);

        double total = 0;
        for (GenPowerEntity entity: results) {
            total += entity.getValue();
        }
        return total;
    }


    @Override
    public AnnualCapacityInfo getAnnualCapacityInfo()  {
        Date today = new Date();
        //#1 获取今年的计划发电量
        int year = DateUtil.getYear(today);
        double planCapacity = planPowerDao.findByYear(year).get(0).getPlanPower();

        //#2 年累计发电量
        int genGroupId = stationConfig.getGenCapacityMeterGroup();
        Date beginYear = DateUtil.getFirstDateOfYear(today);
        try {
            beginYear = DateUtil.dateTimeToDate(beginYear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        double annualGenCapacity = calculateGenPowerByGroup(genGroupId, beginYear, today);

        //#3 每月发电量
        List<Double> monthlyCapacity = getMonthCapacity(genGroupId);

        AnnualCapacityInfo annualCapacityInfo = new AnnualCapacityInfo();
        annualCapacityInfo.setAnnualGenCapacity(annualGenCapacity);
        annualCapacityInfo.setAnnualPlanCapacity(planCapacity);
        double completion = annualGenCapacity/planCapacity;
        annualCapacityInfo.setCompletion(completion);
        annualCapacityInfo.setMonthlyPower(monthlyCapacity);

        return annualCapacityInfo;
    }

    @Override
    public List<Double> getOngridCapacityInfo()  {

        int ongridGroupId = stationConfig.getOnGridMeterGroup();
        List<Double> monthlyCapacity = getMonthCapacity(ongridGroupId);

        return monthlyCapacity;
    }

    private List<Double> getMonthCapacity(int groupId)  {
        Date today = new Date();
        Date beginYear = DateUtil.getFirstDateOfYear(today);
        try {
            beginYear = DateUtil.dateTimeToDate(beginYear);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Double> monthlyCapacity = new ArrayList<>();
        int monthSize = DateUtil.getMonthNumInYear();

        Date startMonth = beginYear;
        Date endMonth = beginYear;
        for (int i = 0; i < monthSize; i++) {
            startMonth = endMonth;
            endMonth = DateUtil.dateAddMonths(startMonth, 1);
            if (DateUtil.dateCompare(endMonth, today) == 1) {
                endMonth = today;
            } else if (DateUtil.dateCompare(startMonth, today) == 1) {
                monthlyCapacity.add(0.0);
                continue;
            }

            double monthCapacity = calculateGenPowerByGroup(groupId, startMonth, endMonth);
            monthlyCapacity.add(monthCapacity);
        }

        return monthlyCapacity;
    }
}
