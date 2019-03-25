package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.MeterEntity;
import com.sk.zl.entity.zheling.MeterRateEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 电表倍率
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
public class MeterRate {
    @JsonProperty("id")
    private int id;

    @JsonProperty("start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonProperty("end")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @JsonProperty("value")
    private double rate;

    @JsonIgnore
    private int deleted;

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

    public boolean checkDateValid() {
        if (startTime.getTime() >= endTime.getTime()) {
            deleted = 2;
            return false;
        }

        return true;
    }

    public boolean checkDateConflict(MeterRate other) {
        // id相同的时候，属于更新，无需检查时间范围冲突
        if (this.id != 0 && this.id == other.getId()) {
            return false;
        }

        long s1 = this.getStartTime().getTime();
        long e1 = this.getEndTime().getTime();
        long s2 = other.getStartTime().getTime();
        long e2 = other.getEndTime().getTime();
        long maxStart = (s1 > s2 ? s1 : s2);
        long minEnd = (e1 < e2 ? e1 : e2);

        if (maxStart < minEnd) {
            this.setDeleted(1);
            other.setDeleted(1);
            return true;
        }
        return false;
    }

    public MeterRateEntity toEntity() {
        MeterRateEntity entity = new MeterRateEntity();
        entity.setId(id);
        entity.setRate(rate);
        entity.setStartTime(startTime);
        entity.setEndTime(endTime);
        return entity;
    }


    @Override
    public String toString() {
        return "MeterRate{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rate=" + rate +
                '}';
    }
}
