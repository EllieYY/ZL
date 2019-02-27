package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : 机组发电量信息对比
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
@NoArgsConstructor
public class PlantGenCapacityComparison {
    @JsonProperty("name")
    private String name;

    @JsonProperty("fVal")
    private double firstValue;

    @JsonProperty("sVal")
    private double secondValue;


    public PlantGenCapacityComparison(String name, double firstValue, double secondValue) {
        this.name = name;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
}
