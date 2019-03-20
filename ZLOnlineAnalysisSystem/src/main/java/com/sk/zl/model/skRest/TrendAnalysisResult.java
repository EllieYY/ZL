package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.plant.PlantTrend;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : skd趋势分析返回结果
 * @Author : Ellie
 * @Date : 2019/3/20
 */
@Data
@NoArgsConstructor
public class TrendAnalysisResult {
    @JsonProperty("iret")
    private int iret;

    @JsonProperty("data")
    private List<PlantTrend> data;
}
