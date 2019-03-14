package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 电表表码
 * @Author : Ellie
 * @Date : 2019/3/5
 */
@Data
public class MeterCode {
    @JsonProperty("meterId")
    private int meterId;
    @JsonProperty("time")
    private Date time;
    @JsonProperty("code")
    private double code;

    public MeterCodeEntity toEntity() {
        MeterCodeEntity entity = new MeterCodeEntity();
        entity.setMeterId(meterId);
        entity.setCode(code);
        entity.setTime(time);
        return entity;
    }

    public static MeterCode fromEntity(MeterCodeEntity entity) {
        MeterCode model = new MeterCode();
        model.meterId = entity.getMeterId();
        model.time = entity.getTime();
        model.code = entity.getCode();
        return model;
    }
}
