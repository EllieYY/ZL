package com.sk.zl.service.impl;

import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.MeterRateDao;
import com.sk.zl.dao.meter.PlanPowerDao;
import com.sk.zl.dao.setting.LoginLogDao;
import com.sk.zl.entity.MeterEntity;
import com.sk.zl.entity.MeterRateEntity;
import com.sk.zl.entity.PlanPowerEntity;
import com.sk.zl.model.meter.Meter;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.result.RespEntity;
import com.sk.zl.model.station.AnnualCapacityInfo;
import com.sk.zl.model.station.HydrologicalInfo;
import com.sk.zl.model.station.PowerStationSnapshot;
import com.sk.zl.model.station.StationAlarmNum;
import com.sk.zl.utils.DateUtil;
import com.sk.zl.utils.RespUtil;
import com.sk.zl.model.station.LoginLog;
import com.sk.zl.model.station.PlanPower;
import com.sk.zl.service.StationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public RespEntity<Integer> addLog(LoginLog log) {
        loginLogDao.save(log.toEntity());
        return RespUtil.makeOkResp(0);
    }

    @Override
    public RespEntity<List<Meter>> getMeterInfo() {
        List<MeterEntity> entities = meterDao.findAll();
        List<Meter> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(Meter.fromEntity(item));
        }, ArrayList::addAll);
        return RespUtil.makeOkResp(models);
    }

    @Override
    public RespEntity<List<MeterRate>> getMeterRate(int meterId) {
        List<MeterRateEntity> entities = meterRateDao.findByMeterId(meterId);
        List<MeterRate> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(MeterRate.fromEntity(item));
        }, ArrayList::addAll);
        return RespUtil.makeOkResp(models);
    }

    @Override
    public RespEntity<List<MeterRate>> addMeterRate(int meterId, List<MeterRate> models) {
        MeterEntity meter = meterDao.getOne(meterId);
        if (null == meter) {
            return RespUtil.makeCustomErrResp("电表id不存在");
        }

        List<MeterRateEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity(meter));
        }, ArrayList::addAll);

        meterRateDao.saveAll(entities);
        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<List<MeterRate>> updateMeterRate(MeterRate meterRate) {
        meterRateDao.updateRateById(
                meterRate.getId(),
                meterRate.getRate(),
                meterRate.getStartTime(),
                meterRate.getEndTime());

        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<List<MeterRate>> deleteMeterRate(int rateId) {
        meterRateDao.deleteById(rateId);
        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<List<PlanPower>> getPlanPower() {
        List<PlanPowerEntity> entities = planPowerDao.findAll();
        List<PlanPower> planPowers = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(PlanPower.fromEntity(item));
        }, ArrayList::addAll);

        return RespUtil.makeOkResp(planPowers);
    }

    @Override
    public RespEntity<List<PlanPower>> addPlanPowers(List<PlanPower> models) {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.saveAll(entities);
        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<List<PlanPower>> updatePlanPowers(List<PlanPower> models) {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.saveAll(entities);
        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<List<PlanPower>> deletePlanPowers(List<PlanPower> models) {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.deleteInBatch(entities);
        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<StationAlarmNum> getAlarmNum() {
        // TODO:通过数据库的rest接口读取表示故障条数的测点的值，实际统计程序由后台c程序实现。
        // TODO:暂时给出测试数据
        StationAlarmNum alarmNum = new StationAlarmNum();
        alarmNum.setNumOfAccident(1);
        alarmNum.setNumOfGlitches(3);
        return RespUtil.makeOkResp(alarmNum);
    }

    @Override
    public RespEntity<HydrologicalInfo> getHydrologicalInfo() {
        // TODO:水情系统尚未接入，暂时使用测试数据
        HydrologicalInfo info = new HydrologicalInfo();
        info.setWaterHead(42.56);
        info.setInflow(7.31);
        info.setOutflow(7.31);
        info.setUpstreamWaterLevel(42.56);
        info.setDownstreamWaterLevel(20.56);
        return RespUtil.makeOkResp(info);
    }

    @Override
    public RespEntity<PowerStationSnapshot> getStationSnapshot() {
        // TODO:需要进行电量统计，暂时使用测试数据。
        PowerStationSnapshot stationSnapshot = new PowerStationSnapshot();
        try {
            // 安全生产天数 = 当前日期 - 2005/9/10
            int totalSafetyDays = DateUtil.daysBetweenDate(DateUtil.getNow(), "2005-09-10");

            // 年安全生产天数 = 当前日期在本年中的天数
            int annualSafetyDays = DateUtil.daysOfTodayInYear();

            // 全厂实时总有功：
            // TODO：rest取点统计
            double realTimeActivePower = 29.75;

            // 昨日发电量
            // TODO：待计算
            double preDayGenCapacity = 51;

            // 年累计发电量
            // TODO：待计算
            double annualGenCapacity = 25487;

            // 年累计上网电量
            // TODO：待计算
            double annualOnGridPower = 25467;

            stationSnapshot.setTotalSafetyDays(totalSafetyDays);
            stationSnapshot.setAnnualSafetyDays(annualSafetyDays);
            stationSnapshot.setRealTimeActivePower(realTimeActivePower);
            stationSnapshot.setPreDayGenCapacity(preDayGenCapacity);
            stationSnapshot.setAnnualGenCapacity(annualGenCapacity);
            stationSnapshot.setAnnualOnGridPower(annualOnGridPower);
        } catch (Exception e) {
            e.printStackTrace();
            return RespUtil.makeInnerErrResp();
        }

        return RespUtil.makeOkResp(stationSnapshot);
    }


    @Override
    public RespEntity<AnnualCapacityInfo> getAnnualCapacityInfo() {
        // TODO：需要进行电量统计，目前提供测试数据
        AnnualCapacityInfo annualCapacityInfo = new AnnualCapacityInfo();
        annualCapacityInfo.setAnnualGenCapacity(6000);
        annualCapacityInfo.setAnnualPlanCapacity(7000);
        annualCapacityInfo.setCompletion(6000/7000);
        List<Double> monthluCapacity = Arrays.asList(501.00, 499.00,
                502.00, 498.00, 503.00, 497.00, 504.00, 496.00, 505.00, 495.00, 506.00, 494.00);

        return RespUtil.makeOkResp(annualCapacityInfo);
    }

    @Override
    public RespEntity<List<Double>> getOngridCapacityInfo() {
        // TODO：需要进行电量统计，目前提供测试数据
        List<Double> monthluCapacity = Arrays.asList(501.00, 499.00,
                502.00, 498.00, 503.00, 497.00, 504.00, 496.00, 505.00, 495.00, 506.00, 494.00);

        return RespUtil.makeOkResp(monthluCapacity);
    }


    @Override
    public RespEntity<List<PlantGenerateCapacity>> getGenCapacityRank() {
        // TODO：机组发电量排名
        List<PlantGenerateCapacity> plantList = new ArrayList<PlantGenerateCapacity>();
        plantList.add(new PlantGenerateCapacity("机组1", 96));
        plantList.add(new PlantGenerateCapacity("机组5", 84));
        plantList.add(new PlantGenerateCapacity("机组3", 72));
        plantList.add(new PlantGenerateCapacity("机组6", 54));
        plantList.add(new PlantGenerateCapacity("机组2", 53));
        plantList.add(new PlantGenerateCapacity("机组4", 46));

        return RespUtil.makeOkResp(plantList);
    }

    @Override
    public RespEntity<List<PlantEffectiveHours>> getEffectiveHoursRank() {
        // TODO：机组月利用小时数排名
        List<PlantEffectiveHours> plantList = new ArrayList<PlantEffectiveHours>();
        plantList.add(new PlantEffectiveHours("机组1", 696));
        plantList.add(new PlantEffectiveHours("机组5", 684));
        plantList.add(new PlantEffectiveHours("机组3", 672));
        plantList.add(new PlantEffectiveHours("机组6", 654));
        plantList.add(new PlantEffectiveHours("机组2", 653));
        plantList.add(new PlantEffectiveHours("机组4", 646));
        return RespUtil.makeOkResp(plantList);
    }

    @Override
    public RespEntity<List<PlantGenCapacityComparison>> getPlantComparison(long sTime1, long eTime1,
                                                                           long sTime2, long eTime2) {
        // TODO:待统计电量
        List<PlantGenCapacityComparison> plantList = new ArrayList<PlantGenCapacityComparison>();
        plantList.add(new PlantGenCapacityComparison("机组1", 62, 78));
        plantList.add(new PlantGenCapacityComparison("机组2", 55, 45));
        plantList.add(new PlantGenCapacityComparison("机组3", 35, 25));
        plantList.add(new PlantGenCapacityComparison("机组4", 61, 35));
        plantList.add(new PlantGenCapacityComparison("机组5", 66, 25));
        plantList.add(new PlantGenCapacityComparison("机组6", 49, 45));
        return RespUtil.makeOkResp(plantList);
    }
}
