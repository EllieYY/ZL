package com.sk.zl.service;

import com.sk.zl.model.meter.Meter;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.req.RespEntity;
import com.sk.zl.model.setting.LoginLog;
import com.sk.zl.model.setting.PlanPower;
import com.sk.zl.model.setting.Plant;

import java.util.List;

/**
 * @Description : 为系统设置部分提供服务
 * @Author : Ellie
 * @Date : 2019/2/25
 */
public interface SettingService {
    /** 计划发电量信息修改 */
    List<PlanPower> getPlanPower();
    int addPlanPowers(List<PlanPower> planPowers);
    int updatePlanPowers(List<PlanPower> planPowers);
    int deletePlanPowers(List<PlanPower> planPowers);


    /** 机组信息设置 */
    List<Plant> getPlantInfo();
    int updatePlants(int id, Byte state);

    /** 系统日志 */
    int addLog(LoginLog log);

    /** 电表信息和电表倍率设置 */
    List<Meter> getMeterInfo();
    List<MeterRate> getMeterRate(int meterId);
    RespEntity<String> addMeterRate(int meterId, List<MeterRate> rates);
    int updateMeterRate(MeterRate meterRate);
    int deleteMeterRate(int rateId);

}
