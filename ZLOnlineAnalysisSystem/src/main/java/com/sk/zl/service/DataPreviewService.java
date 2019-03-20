package com.sk.zl.service;

import com.sk.zl.model.plant.PagePlantAnalogPoints;
import com.sk.zl.model.plant.PagePlantDataPreview;
import com.sk.zl.model.plant.PlantFaultPointsStat;
import com.sk.zl.model.plant.PlantRunningAnalysis;
import com.sk.zl.model.plant.PlantTrend;
import com.sk.zl.model.request.ReDataPreview;
import com.sk.zl.model.request.RePlantAnalogs;
import com.sk.zl.model.request.RePlantTrend;
import com.sk.zl.model.request.ReDataAnalysis;

import java.util.List;

/**
 * @Description : 数据预览
 * @Author : Ellie
 * @Date : 2019/3/7
 */
public interface DataPreviewService {
    /** 一览表查询和报警查询 */
    PagePlantDataPreview getWarningData(ReDataPreview condition);

    /** 测点分析 */
    List<PlantFaultPointsStat> getPlantFaultsStat();
    PagePlantAnalogPoints getAnalogPointsById(RePlantAnalogs rePlantAnalogs);

    /** 趋势分析 */
    List<PlantTrend> getPlantTrend(ReDataAnalysis condition);

    /** 开停机分析 */
    List<PlantRunningAnalysis> getRunningTimeAnalysis(ReDataAnalysis condition);
}
