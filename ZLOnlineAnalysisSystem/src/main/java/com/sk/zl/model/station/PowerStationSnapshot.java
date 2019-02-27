package com.sk.zl.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 电厂信息预览
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
public class PowerStationSnapshot {
    /** 安全生产天数 */
    @JsonProperty("safeDays")
    private int totalSafetyDays;

    /** 年安全生产天数 */
    @JsonProperty("safeDaysOfYear")
    private int annualSafetyDays;

    /** 有功功率 */
    @JsonProperty("activeTotalPower")
    private double realTimeActivePower;

    /** 昨日发电量 */
    @JsonProperty("yesterdayPower")
    private double preDayGenCapacity;

    /** 年发电量 */
    @JsonProperty("totalYearPower")
    private double annualGenCapacity;

    /** 年累计上网电量 */
    @JsonProperty("totalYearNetPower")
    private double annualOnGridPower;
}
