package com.sk.zl.service;

import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.plant.PlantState;
import com.sk.zl.model.result.RespEntity;

import java.util.List;

/**
 * @Description : 提供机组相关信息的获取
 * @Author : Ellie
 * @Date : 2019/2/27
 */
public interface PlantService {
    /** 机组检修状态 */
    RespEntity<List<PlantState>> getPlantsState();
    RespEntity<List<PlantState>> updatePlantsState(int id, Byte state);

    /** 机组测点数据查看 */
    RespEntity<List<PlantPointSnapshot>> getPointSnapshot();


}
