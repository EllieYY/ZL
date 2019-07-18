package com.sk.zl.service.impl;

import com.sk.zl.aop.excption.ServiceException;
import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoEx;
import com.sk.zl.dao.setting.PlantDao;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.PlantDto;
import com.sk.zl.entity.zheling.PlantEntity;
import com.sk.zl.entity.zheling.PlantLiteEntity;
import com.sk.zl.model.plant.Plant;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.plant.PlantState;
import com.sk.zl.model.request.ReTimeSlots;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.service.PlantService;
import com.sk.zl.service.PowerCalculateService;
import com.sk.zl.utils.DateUtil;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : 实现机组信息获取
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Service
public class PlantServiceImpl implements PlantService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private PlantDao plantDao;

    @Resource
    private PointInfoDao pointInfoDao;

    @Resource
    private PowerCalculateService powerCalculateService;


    @Override
    public List<PlantState> getPlantsState()  {
        List<PlantEntity> entities = plantDao.findAll();

//        List<PlantDto> plantDtos = plantDao.findAllByCapacity(45000);
//
//        for (int i = 0; i < plantDtos.size(); i++) {
//            System.out.println(plantDtos.get(i).getId() + "    " + plantDtos.get(i).getName());
//        }
//        System.out.println(plantDtos);
//
//
//        List<PlantEntity> entities = new ArrayList<>();
        List<PlantState> plants = entities.stream().collect(ArrayList::new, (list, item) -> {
            Plant plant = Plant.fromEntity(item);
            list.add(plant.toPlantState());
        }, ArrayList::addAll);
        return plants;
    }

    @Override
    public List<PlantState> updatePlantsState(List<PlantState> models)  {
        for (PlantState plant: models) {
            plantDao.updateStateById(plant.getId(), plant.getState());
        }
        return null;
    }

    @Override
    public List<PlantPointSnapshot> getPointSnapshot()  {
        //#1 读取机组信息
        List<PlantEntity> entities = plantDao.findAll();
//        List<PlantEntity> entities = new ArrayList<>();
        List<PlantPointSnapshot> plantPointSnapshots = entities.stream().collect(ArrayList::new, (list, item) -> {
            list.add(PlantPointSnapshot.fromEntity(item));
        }, ArrayList::addAll);

        //#2 通过数据库rest接口读取点的实时数据返回给前端
        for (PlantPointSnapshot plant: plantPointSnapshots) {
            List<PointInfo> points = pointInfoDao.findPlantSnapshotPointsById(plant.getId());
            if (points.size() != 3) {
                continue;
            }
            plant.setActivePower(points.get(0).getValue());
            plant.setReactivePower(points.get(1).getValue());
            plant.setGuideVaneOpening(points.get(2).getValue());
        }

        return plantPointSnapshots;
    }


    @Override
    public List<PlantGenerateCapacity> getGenCapacityRank(PowerType type)  {
        //#1 获取机组信息 - 机组对应的电表
        List<PlantEntity> plants = plantDao.findAll();
//        List<PlantEntity> plants = new ArrayList<>();
        List<PlantGenerateCapacity> plantList = new ArrayList<PlantGenerateCapacity>();

        //#2 统计当月电量
        Date today = new Date();
        Date start = today;
        Date end = today;
        if (type == PowerType.MONTH) {
            start = DateUtil.getFirstDateOfMonth(today);
        } else if (type == PowerType.YESTERDAY) {
            start = DateUtil.dateAddDays(today, -1, false);
            end = DateUtil.dateTimeToDate(today);
        }
        start = DateUtil.dateTimeToDate(start);

        for (PlantEntity plant : plants) {
            int meterId = plant.getMeter().getId();
            double value = powerCalculateService.calculateGenPowerByMeter(meterId, start, end);
            plantList.add(new PlantGenerateCapacity(plant.getName(), value));
        }

        log.debug("start="+ start + ", end=" + end + "." + plantList.toString());
        return plantList;
    }

    @Override
    public List<PlantEffectiveHours> getEffectiveHoursRank()  {
        //#1 获取机组信息 - 机组对应的电表
        List<PlantEntity> plants = plantDao.findAll();
//        List<PlantEntity> plants = new ArrayList<>();
        List<PlantEffectiveHours> plantList = new ArrayList<PlantEffectiveHours>();

        //#2 统计
        Date today = new Date();
        Date monthBegin = DateUtil.getFirstDateOfMonth(today);
        try {
            monthBegin = DateUtil.dateTimeToDate(monthBegin);
        } catch (Exception e) {
            throw new ServiceException(e.toString());
        }

        Date preMonth = DateUtil.dateAddMonths(monthBegin,-1);
        for (PlantEntity plant : plants) {
            int meterId = plant.getMeter().getId();
            double power = powerCalculateService.calculateGenPowerByMeter(meterId, preMonth, monthBegin);
            double capacity = plant.getCapacity();

            double value = power / capacity;

            plantList.add(new PlantEffectiveHours(plant.getName(), value));
        }
        return plantList;
    }


    @Override
    public List<PlantGenCapacityComparison> getPlantComparison(ReTimeSlots timeSlots)  {
        //#1 获取机组信息 - 机组对应的电表
        List<PlantEntity> plants = plantDao.findAll();
//        List<PlantEntity> plants = new ArrayList<>();
        List<PlantGenCapacityComparison> plantList = new ArrayList<PlantGenCapacityComparison>();

        //#2 统计月利用小时数
        for (PlantEntity plant : plants) {
            int meterId = plant.getMeter().getId();
            double value1 = powerCalculateService.calculateGenPowerByMeter(meterId, timeSlots.getSTime1(), timeSlots.getETime1());
            double value2 = powerCalculateService.calculateGenPowerByMeter(meterId, timeSlots.getSTime2(), timeSlots.getETime2());
            plantList.add(new PlantGenCapacityComparison(plant.getName(), value1, value2));
        }
        return plantList;
    }

}
