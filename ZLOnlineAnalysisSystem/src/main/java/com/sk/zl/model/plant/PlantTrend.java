package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 机组趋势分析信息
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class PlantTrend {
    @JsonProperty("name")
    private String name;

    @JsonProperty("faultNum")
    private int faultNum;

    @JsonProperty("rumNum")
    private int runNum;

    @JsonProperty("time")
    private float runTime;

    public PlantTrend(String name, int faultNum, int runNum, float runTime) {
        this.name = name;
        this.faultNum = faultNum;
        this.runNum = runNum;
        this.runTime = runTime;
    }

    public PlantTrend() {
    }
}
