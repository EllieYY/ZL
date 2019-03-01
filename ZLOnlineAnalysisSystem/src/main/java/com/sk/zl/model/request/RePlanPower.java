package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.station.PlanPower;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Description : 接收参数-计划发电量
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
@NoArgsConstructor
public class RePlanPower {
    @JsonProperty("type")
    private String type;

    @JsonProperty("powers")
    private List<PlanPower> planPowerList;
}
