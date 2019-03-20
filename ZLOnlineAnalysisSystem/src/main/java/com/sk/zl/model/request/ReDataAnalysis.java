package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 开停机分析参数
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class ReDataAnalysis {
    @JsonProperty("id")
    private int id;

    @JsonProperty("startTime")
    private Date startTime;

    @JsonProperty("endTime")
    private Date endTime;

    @Override
    public String toString() {
        return "ReDataAnalysis{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
