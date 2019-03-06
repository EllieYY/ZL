package com.sk.zl.model.meter;

import com.sk.zl.entity.MeterCodeEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/5
 */
@Data
public class MeterCode {
    private int meterId;
    private Date time;
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
