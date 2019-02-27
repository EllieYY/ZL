package com.sk.zl.service;

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
import com.sk.zl.model.station.LoginLog;
import com.sk.zl.model.station.PlanPower;

import java.util.List;

/**
 * @Description : 发电厂信息服务
 * @Author : Ellie
 * @Date : 2019/2/25
 */
public interface StationService {
    /** 计划发电量信息修改 */
    RespEntity<List<PlanPower>> getPlanPower();
    RespEntity<List<PlanPower>> addPlanPowers(List<PlanPower> planPowers);
    RespEntity<List<PlanPower>> updatePlanPowers(List<PlanPower> planPowers);
    RespEntity<List<PlanPower>> deletePlanPowers(List<PlanPower> planPowers);

    /** 系统日志 */
    RespEntity<Integer> addLog(LoginLog log);

    /** 电表信息和电表倍率设置 */
    RespEntity<List<Meter>> getMeterInfo();
    RespEntity<List<MeterRate>> getMeterRate(int meterId);
    RespEntity<List<MeterRate>> addMeterRate(int meterId, List<MeterRate> rates);
    RespEntity<List<MeterRate>> updateMeterRate(MeterRate meterRate);
    RespEntity<List<MeterRate>> deleteMeterRate(int rateId);

    /** 全厂报警条数 */
    RespEntity<StationAlarmNum> getAlarmNum();

    /** 水情系统 */
    RespEntity<HydrologicalInfo> getHydrologicalInfo();

    /** 发电厂信息快照 */
    RespEntity<PowerStationSnapshot> getStationSnapshot();

    /** 发电量完成情况 */
    RespEntity<AnnualCapacityInfo> getAnnualCapacityInfo();
    RespEntity<List<Double>> getOngridCapacityInfo();

    /** 机组发电量/月利用小时数排名 */
    RespEntity<List<PlantGenerateCapacity>> getGenCapacityRank();
    RespEntity<List<PlantEffectiveHours>> getEffectiveHoursRank();

    /** 机组发电量信息对比 */
    RespEntity<List<PlantGenCapacityComparison>> getPlantComparison(long sTime1, long eTime1,
                                                                    long sTime2, long eTime2);
}
