package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.MeterCodeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description : 电表表码
 * @Author : Ellie
 * @Date : 2019/3/5
 */
@Data
@NoArgsConstructor
public class MeterCode {
    @JsonProperty("id")
    private int meterNodeId;

    @JsonProperty("time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    @JsonProperty("code24")
    private double code;

    @JsonIgnore
    public MeterCodeEntity toEntity() {
        MeterCodeEntity entity = new MeterCodeEntity();
        entity.setMeterNodeId(meterNodeId);
        entity.setCode(code);
        entity.setTime(time);
        return entity;
    }

    @JsonIgnore
    public static MeterCode fromEntity(MeterCodeEntity entity) {
        MeterCode model = new MeterCode();
        model.meterNodeId = entity.getMeterNodeId();
        model.time = entity.getTime();
        model.code = entity.getCode();
        return model;
    }
}
