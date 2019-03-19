package com.sk.zl.service;

import com.sk.zl.model.plant.PlantDataPreview;
import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.model.request.RePlantAnalogs;
import com.sk.zl.model.request.RePlantTrend;
import com.sk.zl.model.request.ReRunningTimeAnalysis;

import java.util.List;

/**
 * @Description : 数据预览
 * @Author : Ellie
 * @Date : 2019/3/7
 */
public interface DataPreviewService {
    /** 一览表查询和报警查询 */
    List<PlantDataPreview> getWarningData(ReDataPreview condition);

    /** 测点分析 */
    List<PlantFaultPointsStat> getPlantFaultsStat();
    List<String> getAnalogPointsById(RePlantAnalogs rePlantAnalogs);

    /** 趋势分析 */
    List<PlantTrend> getPlantTrend(RePlantTrend condition);

    /** 开停机分析 */
    List<PlantRunningAnalysis> getRunningTimeAnalysis(ReRunningTimeAnalysis condition);
}
