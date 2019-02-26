package com.sk.zl.service.impl;

import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.MeterRateDao;
import com.sk.zl.dao.meter.PlanPowerDao;
import com.sk.zl.dao.setting.LoginLogDao;
import com.sk.zl.dao.setting.PlantDao;
import com.sk.zl.entity.MeterEntity;
import com.sk.zl.entity.MeterRateEntity;
import com.sk.zl.entity.PlanPowerEntity;
import com.sk.zl.entity.PlantEntity;
import com.sk.zl.model.meter.Meter;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.req.RespEntity;
import com.sk.zl.model.req.RespUtil;
import com.sk.zl.model.setting.LoginLog;
import com.sk.zl.model.setting.PlanPower;
import com.sk.zl.model.setting.Plant;
import com.sk.zl.service.SettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Service
public class SettingServiceImpl implements SettingService {
    @Resource
    PlantDao plantDao;
    @Resource
    LoginLogDao loginLogDao;
    @Resource
    MeterDao meterDao;
    @Resource
    MeterRateDao meterRateDao;
    @Resource
    PlanPowerDao planPowerDao;

    @Override
    public List<Plant> getPlantInfo() {
        List<PlantEntity> entities = plantDao.findAll();
        List<Plant> plants = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(Plant.fromEntity(item));
        }, ArrayList::addAll);
        return plants;
    }

    @Override
    public int updatePlants(int id, Byte state) {
        return plantDao.updateStateById(id, state);
    }

    @Override
    public int addLog(LoginLog log) {
        loginLogDao.save(log.toEntity());
        return 0;
    }

    @Override
    public List<Meter> getMeterInfo() {
        List<MeterEntity> entities = meterDao.findAll();
        List<Meter> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(Meter.fromEntity(item));
        }, ArrayList::addAll);
        return models;
    }

    @Override
    public List<MeterRate> getMeterRate(int meterId) {
        List<MeterRateEntity> entities = meterRateDao.findByMeterId(meterId);
        List<MeterRate> models = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(MeterRate.fromEntity(item));
        }, ArrayList::addAll);
        return models;
    }

    @Override
    public RespEntity<String> addMeterRate(int meterId, List<MeterRate> models) {
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
    public int updateMeterRate(MeterRate meterRate) {
        return meterRateDao.updateRateById(
                meterRate.getId(),
                meterRate.getRate(),
                meterRate.getStartTime(),
                meterRate.getEndTime());
    }

    @Override
    public int deleteMeterRate(int rateId) {
        meterRateDao.deleteById(rateId);
        return 0;
    }


    @Override
    public List<PlanPower> getPlanPower() {

        List<PlanPowerEntity> entities = planPowerDao.findAll();
        List<PlanPower> planPowers = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(PlanPower.fromEntity(item));
        }, ArrayList::addAll);
        return planPowers;
    }

    @Override
    public int addPlanPowers(List<PlanPower> models) {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.saveAll(entities);
        return 0;
    }

    @Override
    public int updatePlanPowers(List<PlanPower> models) {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.saveAll(entities);
        return 0;
    }

    @Override
    public int deletePlanPowers(List<PlanPower> models) {
        List<PlanPowerEntity> entities = models.stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.toEntity());
        }, ArrayList::addAll);
        planPowerDao.deleteInBatch(entities);
        return 0;
    }
}
