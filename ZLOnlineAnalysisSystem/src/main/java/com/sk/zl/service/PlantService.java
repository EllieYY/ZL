package com.sk.zl.service;

import com.sk.zl.model.plant.PlantEffectiveHours;
import com.sk.zl.model.plant.PlantGenCapacityComparison;
import com.sk.zl.model.plant.PlantGenerateCapacity;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.plant.PlantState;
import com.sk.zl.model.request.ReTimeSlots;
import com.sk.zl.model.result.ResultBean;

import java.util.List;

/**
 * @Description : 提供机组相关信息的获取
 * @Author : Ellie
 * @Date : 2019/2/27
 */
public interface PlantService {
    /** 机组检修状态 */
    List<PlantState> getPlantsState() ;
    List<PlantState> updatePlantsState(List<PlantState> models) ;

    /** 机组测点数据查看 */
    List<PlantPointSnapshot> getPointSnapshot();

    /** 机组发电量/月利用小时数排名 */
    List<PlantGenerateCapacity> getGenCapacityRank() ;
    List<PlantEffectiveHours> getEffectiveHoursRank() ;

    /** 机组发电量信息对比 */
    List<PlantGenCapacityComparison> getPlantComparison(ReTimeSlots timeSlots) ;
}
