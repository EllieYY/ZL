package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 趋势分析查询
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class RePlantTrend {
    @JsonProperty("id")
    private int id;

    @JsonProperty("startTime")
    private Date startTime;

    @JsonProperty("endTime")
    private Date endTime;
}
