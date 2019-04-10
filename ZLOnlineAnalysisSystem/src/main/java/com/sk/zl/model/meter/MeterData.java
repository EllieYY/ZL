package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description :
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Data
public class MeterData {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("code0")
    private double code0;

    @JsonProperty("code24")
    private double code24;

    @JsonProperty("rate")
    private double rate;

    @JsonProperty("curMonthPower")
    private double curMonthPower;

    @JsonProperty("curYearPower")
    private double curYearPower;

    public MeterData(int id, String name, double code0, double code24, double rate, double curMonthPower, double curYearPower) {
        this.id = id;
        this.name = name;
        this.code0 = code0;
        this.code24 = code24;
        this.rate = rate;
        this.curMonthPower = curMonthPower;
        this.curYearPower = curYearPower;
    }
}
