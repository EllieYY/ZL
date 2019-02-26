package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.MeterEntity;
import com.sk.zl.entity.MeterRateEntity;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
public class MeterRate {
    @JsonProperty("id")
    private int id;
    @JsonProperty("start")
    private Timestamp startTime;
    @JsonProperty("end")
    private Timestamp endTime;
    @JsonProperty("value")
    private double rate;

    public static MeterRate fromEntity(MeterRateEntity entity) {
        MeterRate model = new MeterRate();
        model.setId(entity.getId());
        model.setStartTime(entity.getStartTime());
        model.setEndTime(entity.getEndTime());
        model.setRate(entity.getRate());

        return model;
    }

    public MeterRateEntity toEntity(MeterEntity meter) {
        MeterRateEntity entity = new MeterRateEntity();
        entity.setRate(rate);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        entity.setMeter(meter);
        return entity;
    }
}
