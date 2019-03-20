package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.plant.PlantRunningAnalysis;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : skd开停机分析结果
 * @Author : Ellie
 * @Date : 2019/3/20
 */
@Data
@NoArgsConstructor
public class RunningAnalysisResult {
    @JsonProperty("iret")
    private int iret;

    @JsonProperty("data")
    private List<PlantRunningAnalysis> data;
}
