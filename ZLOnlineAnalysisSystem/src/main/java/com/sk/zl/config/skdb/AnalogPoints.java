package com.sk.zl.config.skdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description : 机组故障统计模拟量测点
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Data
public class AnalogPoints {
    @JsonProperty("id")
    private int id;

    @JsonProperty("points")
    private List<String> points;

    @Override
    public String toString() {
        return "AnalogPoints{" +
                "id=" + id +
                ", points=" + points +
                '}';
    }
}
