package com.sk.zl.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
public class AnnualCapacityInfo {
    /** 每月电量，大小固定为12的数组，依次表示1~12月 */
    @JsonProperty("monthlyPower")
    private List<Double> monthlyPower;

    /** 全年累计发电量 */
    @JsonProperty("totalPower")
    private double annualGenCapacity;

    /** 全年计划电量 */
    @JsonProperty("planPower")
    private double annualPlanCapacity;

    /** 发电量完成率 */
    @JsonProperty("completion")
    private double completion;

}
