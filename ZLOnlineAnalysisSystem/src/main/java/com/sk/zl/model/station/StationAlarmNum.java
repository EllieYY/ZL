package com.sk.zl.model.station;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 报警条数
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
public class StationAlarmNum {
    /** 事故条数 */
    @JsonProperty("accident")
    private int numOfAccident;


    /** 故障条数 */
    @JsonProperty("fault")
    private int numOfGlitches;
}
