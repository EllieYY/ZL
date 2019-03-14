package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description : 报表小分类
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Data
public class MeterGroup {
    @JsonProperty("groupName")
    private String name;

    @JsonProperty("meters")
    private List<MeterData> meters;


    public MeterGroup(String name, List<MeterData> meters) {
        this.name = name;
        this.meters = meters;
    }
}
