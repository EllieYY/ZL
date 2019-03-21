package com.sk.zl.service.impl;

import com.sk.zl.aop.excption.ServiceException;
import com.sk.zl.dao.meter.MeterDao;
import com.sk.zl.dao.meter.impl.GenPowerDaoEx;
import com.sk.zl.dao.setting.PlantDao;
import com.sk.zl.dao.skdb.PointInfoDao;
import com.sk.zl.entity.zheling.GenPowerEntity;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.PlantEntity;
import com.sk.zl.model.plant.Plant;
import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.plant.PlantState;
import com.sk.zl.model.request.ReTimeSlots;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.service.PlantService;
import com.sk.zl.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private GenPowerDaoEx genPowerDaoEx;

    @Resource
    private MeterDao meterDao;

    @Override
    public List<PlantState> getPlantsState()  {
        List<PlantEntity> entities = plantDao.findAll();
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
    public List<PlantGenerateCapacity> getGenCapacityRank()  {
        //#1 获取机组信息 - 机组对应的电表
        List<PlantEntity> plants = plantDao.findAll();
        List<PlantGenerateCapacity> plantList = new ArrayList<PlantGenerateCapacity>();

        //#2 统计当月电量
        Date today = new Date();
        Date monthBegin = DateUtil.getFirstDateOfMonth(today);

        monthBegin = DateUtil.dateTimeToDate(monthBegin);


        for (PlantEntity plant : plants) {
            int meterId = plant.getMeter().getId();
            double value = calculateGenPowerByMeter(meterId, monthBegin, today);
            plantList.add(new PlantGenerateCapacity(plant.getName(), value));
        }

        return plantList;
    }

    @Override
    public List<PlantEffectiveHours> getEffectiveHoursRank()  {
        //#1 获取机组信息 - 机组对应的电表
        List<PlantEntity> plants = plantDao.findAll();
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
            double power = calculateGenPowerByMeter(meterId, preMonth, monthBegin);
            double capacity = plant.getCapacity();

            double value = power / capacity;

            plantList.add(new PlantEffectiveHours(plant.getName(), value));
        }
        return plantList;
    }


    private double calculateGenPowerByMeter(int meterId, Date startTime, Date endTime) {
        // 获取meter下node的id集合
        MeterEntity meter = meterDao.findById(meterId).get();
        List<Integer> ids = meter.getNodeSet().stream().collect(ArrayList::new, (list, item) -> {
            list.add(item.getId());
        }, ArrayList::addAll);

        List<GenPowerEntity> results = genPowerDaoEx.findByNodeIdsAndTime(ids, startTime, endTime);

        log.info("meterId" + meterId + "     #time: " + startTime + " ~ " + endTime);
        log.info(results.toString());

        double total = 0;
        for (GenPowerEntity entity: results) {
            total += entity.getValue();
        }
        return total;
    }


    @Override
    public List<PlantGenCapacityComparison> getPlantComparison(ReTimeSlots timeSlots)  {
        //#1 获取机组信息 - 机组对应的电表
        List<PlantEntity> plants = plantDao.findAll();

        List<PlantGenCapacityComparison> plantList = new ArrayList<PlantGenCapacityComparison>();

        //#2 统计月利用小时数
        for (PlantEntity plant : plants) {
            int meterId = plant.getMeter().getId();
            double value1 = calculateGenPowerByMeter(meterId, timeSlots.getSTime1(), timeSlots.getETime1());
            double value2 = calculateGenPowerByMeter(meterId, timeSlots.getSTime2(), timeSlots.getETime2());
            plantList.add(new PlantGenCapacityComparison(plant.getName(), value1, value2));
        }
        return plantList;
    }

}
