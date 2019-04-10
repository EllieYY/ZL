package com.sk.zl.service.impl;

import com.sk.zl.aop.excption.DataDaoException;
import com.sk.zl.aop.excption.ServiceException;
import com.sk.zl.config.skdb.StationConfig;
import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.MeterGroupDao;
import com.sk.zl.dao.meter.MeterRateDao;
import com.sk.zl.dao.meter.PlanPowerDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoEx;
import com.sk.zl.dao.meter.impl.MeterRateDaoEx;
import com.sk.zl.dao.preview.NowAlarmDao;
import com.sk.zl.dao.setting.LoginLogDao;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.LoginLogEntity;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.MeterGroupEntity;
import com.sk.zl.entity.zheling.MeterRateEntity;
import com.sk.zl.entity.zheling.PlanPowerEntity;
import com.sk.zl.model.meter.DateSection;
import com.sk.zl.model.meter.MeterInfo;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.meter.ReNewName;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.model.station.AnnualCapacityInfo;
import com.sk.zl.model.station.HydrologicalInfo;
import com.sk.zl.model.station.PowerStationSnapshot;
import com.sk.zl.model.station.StationAlarmNum;
import com.sk.zl.service.PowerCalculateService;
import com.sk.zl.utils.DateUtil;
import com.sk.zl.model.station.LoginLog;
import com.sk.zl.model.station.PlanPower;
import com.sk.zl.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoginLogDao loginLogDao;

    @Resource
    private MeterDao meterDao;

    @Resource
    private MeterGroupDao meterGroupDao;

    @Resource
    private MeterRateDao meterRateDao;

    @Resource
    MeterRateDaoEx meterRateDaoEx;

    @Resource
    private PlanPowerDao planPowerDao;

    @Resource
    private PointInfoDao pointInfoDao;

    @Autowired
    private PowerCalculateService powerCalculateService;

    @Resource
    private StationConfig stationConfig;

    @Resource
    private NowAlarmDao nowAlarmDao;

    @Autowired
    private AsyncTaskService taskService;


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
    public List<MeterInfo> getMeterInfo()  {
        List<MeterEntity> entities = meterDao.findAll();
        List<MeterInfo> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(MeterInfo.fromEntity(item));
        }, ArrayList::addAll);
        return models;
    }

    @Override
    public ReNewName setMeterName(int id, String newName) {
        MeterEntity entity = meterDao.findById(id).get();
        if (null == entity) {
            return null;
        }

        entity.setName(newName);
        meterDao.save(entity);
        return new ReNewName(entity.getId(), entity.getName());
    }

    @Override
    public ReNewName setMeterGroupName(int id, String newName) {
        MeterGroupEntity entity = meterGroupDao.findById(id);
        if (null == entity) {
            return null;
        }

        entity.setName(newName);
        meterGroupDao.save(entity);
        return new ReNewName(entity.getId(), entity.getName());
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
    public boolean checkValidMeterId(int meterId) {
        List<MeterEntity> meters = meterDao.findAll();
        for (MeterEntity meter : meters) {
            if (meter.getId() == meterId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<MeterRate> checkValidRate(List<MeterRate> rates) {
        List<MeterRate> invalidRates = new ArrayList<MeterRate>();
        for (MeterRate rate : rates) {
            MeterRateEntity rateEntities = meterRateDao.findById(rate.getId());
            if (null == rateEntities) {
                invalidRates.add(rate);
                continue;
            }

            MeterEntity meter = meterRateDao.findById(rate.getId()).getMeter();
            if (null == meter) {
                invalidRates.add(rate);
                continue;
            }
        }
        return invalidRates;
    }

    @Override
    public List<MeterRate> checkRateUpdateTime(List<MeterRate> rates) {
        List<MeterRate> invalidRates = new ArrayList<MeterRate>();
        for (MeterRate rate : rates) {
            MeterRateEntity rateEntities = meterRateDao.findById(rate.getId());
            if (null == rateEntities) {
                continue;
            }
            Date updateTime = rate.getUpdateTime();
            if (null == updateTime) {
                continue;
            }

            if (rateEntities.getUpdateTime().after(rate.getUpdateTime())) {
                invalidRates.add(MeterRate.fromEntity(rateEntities));
            }
        }
        return invalidRates;
    }

    @Override
    public List<MeterRate> checkRateConflicts(int meterId, List<MeterRate> originalRates) {
        List<MeterRate> conflictsRates = new ArrayList<MeterRate>();
        //#1 更新的倍率之间冲突
        originalRates = innerRateConflicts(originalRates, conflictsRates);
        log.debug("inner conflicts: " + conflictsRates);
        log.debug("valid rates: " + originalRates);
        if (originalRates.size() == 0) {
            return conflictsRates;
        }

        //#2 更新的倍率与原有倍率冲突
        List<MeterRateEntity> entities = meterRateDao.findByMeterId(meterId);
        if (entities.size() != 0) {
            originalRates = outerRateConflicts(entities, originalRates, conflictsRates);
            log.debug("outer conflicts: " + conflictsRates);
            log.debug("valid rates: " + originalRates);
            if (originalRates.size() == 0) {
                return conflictsRates;
            }
        }
        return conflictsRates;
    }

    @Override
    public int getMeterIdForRate(List<MeterRate> rates) {
        if (rates.size() == 0) {
            throw new DataDaoException("电表倍率无效，rates = " + rates);
        }
        return meterRateDao.findById(rates.get(0).getId()).getMeter().getId();
    }

    @Override
    public List<MeterRate> addMeterRate(int meterId, List<MeterRate> models)  {
        MeterEntity meter = meterDao.getOne(meterId);
        if (null == meter) {
            throw new DataDaoException("电表不存在: meterid = " + meterId);
        }

        return addRates(meterId, models);
    }

    @Override
    public List<MeterRate> updateMeterRate(int meterId, List<MeterRate> meterRaters) {
        List<MeterRate> conflictRates = addRates(meterId, meterRaters);

        return conflictRates;
    }

    @Override
    public List<MeterRate> deleteMeterRate(List<MeterRate> models)  {
        List<MeterRateEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);

        meterRateDao.deleteInBatch(entities);
        return null;
    }

    /** 返回的是存在冲突的倍率信息 */
    private List<MeterRate> addRates(int meterId, List<MeterRate> originalRates) {
        //#3 添加到数据库，并更新发电量
        List<MeterRate> updatedRates = new ArrayList<MeterRate>();
        for (MeterRate newRate: originalRates) {
            log.debug("#metreRateUpdate#meter rate to update: meterId#" + meterId + ", rate#" + newRate);

            List<DateSection> updateDate = new ArrayList<DateSection>();
            DateSection rateSection = new DateSection(newRate.getStartTime(), newRate.getEndTime());

            // 判断更新类型
            // 2019-03-27 为方便测试，禁用时间过滤的功能。
            boolean filterTime = false;
            if (newRate.getId() != 0 && filterTime) {
                MeterRateEntity oldRate = meterRateDaoEx.findById(newRate.getId());
                if (oldRate.getRate() == newRate.getRate()) {
                    updateDate = DateSection.differenceSet(
                            rateSection,
                            new DateSection(oldRate.getStartTime(), oldRate.getEndTime()));
                } else {
                    updateDate.add(rateSection);
                }
            } else {
                updateDate.add(rateSection);
            }

            MeterEntity meter = meterDao.getOne(meterId);
            MeterRateEntity meterRateEntity = newRate.toEntity(meter);
            meterRateDaoEx.save(meterRateEntity);
            MeterRate rate = MeterRate.fromEntity(meterRateDao.findById(meterRateEntity.getId()));
            updatedRates.add(rate);

            taskService.metreRateUpdate(updateDate, meterId, newRate);
        }

        return updatedRates;
    }

    /** 找出互相之间有时间范围冲突的更新倍率 */
    private List<MeterRate> innerRateConflicts(List<MeterRate> originalRates, List<MeterRate> conflictsRate) {
        int size = originalRates.size();
        for (int i = 0; i < size - 1; i++)  {
            MeterRate base = originalRates.get(i);
            if (!base.checkDateValid()) {
                continue;
            }

            for (int j = i + 1; j < size; j++) {
                MeterRate refer = originalRates.get(j);
                if (!refer.checkDateValid()) {
                    continue;
                }
                base.checkDateConflict(refer);
            }
        }

        Iterator<MeterRate> iterator = originalRates.iterator();
        while (iterator.hasNext()) {
            MeterRate rate = iterator.next();
            if (rate.getDeleted() != 0) {
                conflictsRate.add(rate);
                iterator.remove();
            }
        }
        return originalRates;
    }

    /** 与数据库中已存在的倍率之间冲突的更新倍率 */
    private List<MeterRate> outerRateConflicts(List<MeterRateEntity> existRates,
                                               List<MeterRate> newRates,
                                               List<MeterRate> conflictRates) {
        List<MeterRate> validRates = new ArrayList<MeterRate>();
        for (MeterRate newRate : newRates) {
            for (MeterRateEntity existRate : existRates) {
                //# 判断时间区冲突
                MeterRate rate = MeterRate.fromEntity(existRate);
                if (newRate.checkDateConflict(rate)) {
                    conflictRates.add(rate);
                    continue;
                }

                validRates.add(newRate);
            }
        }

        newRates = validRates;

        return newRates;
    }


    @Override
    public List<PlanPower> getPlanPower()  {
        Sort sort = new Sort(Sort.Direction.DESC, "year");
        List<PlanPowerEntity> entities = planPowerDao.findAll(sort);
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

//    @Override
//    public StationAlarmNum getStationAlarmNum()  {
//        List<PointInfo> points = pointInfoDao.findStationAlarmPoints();
//
//        if (points.size() != 2) {
//            throw new RuntimeException("未找到报警点。");
//        }
//
//        StationAlarmNum alarmNum = new StationAlarmNum();
//        alarmNum.setNumOfAccident(new Double(points.get(0).getValue()).intValue());
//        alarmNum.setNumOfGlitches(new Double(points.get(1).getValue()).intValue());
//        return alarmNum;
//    }
    // 2019-04-01修改需求，要求从mysql中读取条数
    @Override
    public StationAlarmNum getStationAlarmNum()  {
        StationAlarmNum alarmNum = new StationAlarmNum();
        alarmNum.setNumOfAccident(nowAlarmDao.countNowAlarmEntitiesByKindIdEquals(stationConfig.getAccidentKindId()));
        alarmNum.setNumOfGlitches(nowAlarmDao.countNowAlarmEntitiesByKindIdEquals(stationConfig.getFaultKindId()));
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
            double preDayGenCapacity = powerCalculateService.calculateGenPowerByGroup(genGroupId, yesterday, today);

            // 年累计发电量
            Date beginYear = DateUtil.getFirstDateOfYear(today);
            beginYear = DateUtil.dateTimeToDate(beginYear);
            double annualGenCapacity = powerCalculateService.calculateGenPowerByGroup(genGroupId, beginYear, today);

            // 年累计上网电量
            int ongridGroupId = stationConfig.getOnGridMeterGroup();
            double annualOnGridPower = powerCalculateService.calculateGenPowerByGroup(ongridGroupId, beginYear, today);

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

        double annualGenCapacity = powerCalculateService.calculateGenPowerByGroup(genGroupId, beginYear, today);

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

            double monthCapacity = powerCalculateService.calculateGenPowerByGroup(groupId, startMonth, endMonth);
            monthlyCapacity.add(monthCapacity);
        }

        return monthlyCapacity;
    }
}
