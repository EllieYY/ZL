package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.MeterEntity;
import com.sk.zl.entity.MeterRateEntity;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

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
    private Date startTime;

    @JsonProperty("end")
    private Date endTime;

    @JsonProperty("value")
    private double rate;

    public static MeterRate fromEntity(MeterRateEntity entity) {
        MeterRate model = new MeterRate();
        model.setId(entity.getId());
        model.setStartTime(new Date(entity.getStartTime().getTime()));
        model.setEndTime(new Date(entity.getEndTime().getTime()));
        model.setRate(entity.getRate());

        return model;
    }

    public MeterRateEntity toEntity(MeterEntity meter) {
        MeterRateEntity entity = new MeterRateEntity();
        entity.setRate(rate);
        entity.setStartTime(new Timestamp(startTime.getTime()));
        entity.setEndTime(new Timestamp(endTime.getTime()));
        entity.setMeter(meter);
        return entity;
    }
}
