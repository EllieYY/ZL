package com.sk.zl.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 水情系统信息
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
public class HydrologicalInfo {
    /** 水头 */
    @JsonProperty("waterTop")
    private double waterHead;

    /** 上游水位 */
    @JsonProperty("upstreamLevel")
    private double upstreamWaterLevel;

    /** 下游水位 */
    @JsonProperty("downstreamLevel")
    private double downstreamWaterLevel;

    /** 无功功率 */
    @JsonProperty("reactivePower")
    private double reactivePower;

    /** 入库流量 */
    @JsonProperty("flow")
    private double inflow;

    /** 出库流量 */
    @JsonProperty("outFlow")
    private double outflow;

}
