package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description : 机组模拟量测点cpid
 * @Author : Ellie
 * @Date : 2019/3/20
 */
@Data
public class PagePlantAnalogPoints {
    @JsonProperty("total")
    private long totalNum;

    @JsonProperty("data")
    private List<String> cpids;
}
