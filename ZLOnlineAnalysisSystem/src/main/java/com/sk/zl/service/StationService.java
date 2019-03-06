package com.sk.zl.service;

import com.sk.zl.entity.GenPowerEntity;
import com.sk.zl.model.meter.Meter;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.request.ReTimeSlots;
import com.sk.zl.model.result.ResultBean;
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
    List<PlanPower> getPlanPower() ;
    List<PlanPower> addPlanPowers(List<PlanPower> planPowers) ;
    List<PlanPower> updatePlanPowers(List<PlanPower> planPowers) ;
    List<PlanPower> deletePlanPowers(List<PlanPower> planPowers) ;

    /** 系统日志 */
    int addLog(LoginLog log) ;
    List<LoginLog> getLog(LoginLog log);

    /** 电表信息和电表倍率设置 */
    List<Meter> getMeterInfo() ;
    List<MeterRate> getMeterRate(int meterId) ;
    List<MeterRate> addMeterRate(int meterId, List<MeterRate> rates) ;
    List<MeterRate> updateMeterRate(int meterId, List<MeterRate> meterRates) ;
    List<MeterRate> deleteMeterRate(List<MeterRate> meterRates) ;

    /** 全厂报警条数 */
    StationAlarmNum getAlarmNum() ;

    /** 水情系统 */
    HydrologicalInfo getHydrologicalInfo() ;

    /** 发电厂信息快照 */
    PowerStationSnapshot getStationSnapshot() ;

    /** 发电量完成情况 */
    AnnualCapacityInfo getAnnualCapacityInfo() ;
    List<Double> getOngridCapacityInfo() ;

    /** 电表码值录入 */
    List<GenPowerEntity> entryMeterCode(List<MeterCode> meterCodes);
}
