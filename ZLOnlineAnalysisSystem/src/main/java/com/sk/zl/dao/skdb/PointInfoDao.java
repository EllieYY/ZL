package com.sk.zl.dao.skdb;

import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.RePlantTrend;
import com.sk.zl.model.request.ReDataAnalysis;
import com.sk.zl.model.skRest.PointInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description : 获取SK数据库的测点信息
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Repository
public interface PointInfoDao {
    List<PointInfo> findRealTimeActivePowerPoints();
    List<PointInfo> findPlantSnapshotPointsById(int plantId);
    List<PointInfo> findStationAlarmPoints();
    List<PlantFaultPointsStat> findPlantFaultPoints();
    List<PlantTrend> findPlantTrendByCondition(ReDataAnalysis condition);
    List<PlantRunningAnalysis> findRunningTimeInfoByCondition(ReDataAnalysis condition);
}
