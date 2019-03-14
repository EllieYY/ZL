package com.sk.zl.service;

import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.model.meter.MeterInfo;
import com.sk.zl.model.meter.MeterCode;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningTimeAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.RePlantAnalogs;
import com.sk.zl.model.request.RePlantTrend;
import com.sk.zl.model.request.ReRunningTimeAnalysis;
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
    List<MeterInfo> getMeterInfo() ;
    List<MeterRate> getMeterRate(int meterId) ;
    List<MeterRate> addMeterRate(int meterId, List<MeterRate> rates) ;
    List<MeterRate> updateMeterRate(int meterId, List<MeterRate> meterRates) ;
    List<MeterRate> deleteMeterRate(List<MeterRate> meterRates) ;

    /** 全厂报警条数 */
    StationAlarmNum getStationAlarmNum();

    /** 测点分析 */
    List<PlantFaultPointsStat> getPlantFaultsStat();
    List<String> getAnalogPointsById(RePlantAnalogs rePlantAnalogs);

    /** 趋势分析 */
    List<PlantTrend> getPlantTrend(RePlantTrend condition);

    /** 开停机分析 */
    List<PlantRunningTimeAnalysis> getRunningTimeAnalysis(ReRunningTimeAnalysis condition);

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
