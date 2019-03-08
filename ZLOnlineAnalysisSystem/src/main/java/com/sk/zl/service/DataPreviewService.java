package com.sk.zl.service;

import com.sk.zl.model.plant.PlantDataPreview;
import com.sk.zl.model.request.ReDataPreview;

import java.util.List;

/**
 * @Description : 数据预览
 * @Author : Ellie
 * @Date : 2019/3/7
 */
public interface DataPreviewService {
    /** 按条件获取机组数据 */
    public List<PlantDataPreview> getPlantData(ReDataPreview condition);

    /** 按条件获取报警数据 */
    public List<PlantDataPreview> getWarningData(ReDataPreview condition);
}
