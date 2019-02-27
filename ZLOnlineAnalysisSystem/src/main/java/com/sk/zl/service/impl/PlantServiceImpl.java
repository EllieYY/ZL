package com.sk.zl.service.impl;

import com.sk.zl.dao.setting.PlantDao;
import com.sk.zl.entity.PlantEntity;
import com.sk.zl.model.plant.Plant;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.plant.PlantState;
import com.sk.zl.model.result.RespEntity;
import com.sk.zl.service.PlantService;
import com.sk.zl.utils.RespUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 实现机组信息获取
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Service
public class PlantServiceImpl implements PlantService {

    @Resource
    PlantDao plantDao;

    @Override
    public RespEntity<List<PlantState>> getPlantsState() {
        List<PlantEntity> entities = plantDao.findAll();
        List<PlantState> plants = entities.stream().collect(ArrayList::new, (list, item) -> {
            Plant plant = Plant.fromEntity(item);
            list.add(plant.toPlantState());
        }, ArrayList::addAll);
        return RespUtil.makeOkResp(plants);
    }

    @Override
    public RespEntity<List<PlantState>> updatePlantsState(int id, Byte state) {
        int ret = plantDao.updateStateById(id, state);
        return RespUtil.makeOkResp();
    }

    @Override
    public RespEntity<List<PlantPointSnapshot>> getPointSnapshot() {
        // TODO:通过数据库rest接口读取点的实时数据返回给前端
        // TODO：暂时使用测试数据
        List<PlantPointSnapshot> plantPointList = new ArrayList<PlantPointSnapshot>();
        plantPointList.add(new PlantPointSnapshot(1, "机组1", 0, 62, 78, 113));
        plantPointList.add(new PlantPointSnapshot(2, "机组2", 0, 55, 45, 95));
        plantPointList.add(new PlantPointSnapshot(3, "机组3", 1, 35, 25, 95));
        plantPointList.add(new PlantPointSnapshot(4, "机组4", 0, 61, 35, 88));
        plantPointList.add(new PlantPointSnapshot(5, "机组5", 0, 66, 78, 35));
        plantPointList.add(new PlantPointSnapshot(6, "机组6", 0, 62, 88, 95));

        return RespUtil.makeOkResp(plantPointList);
    }
}
