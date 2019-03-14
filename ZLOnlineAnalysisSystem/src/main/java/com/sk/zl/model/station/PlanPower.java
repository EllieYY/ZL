package com.sk.zl.model.station;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.PlanPowerEntity;
import lombok.Data;


/**
 * @Description : 计划电量
 * @Author : Ellie
 * @Date : 2019/2/22
 */
@Data
public class PlanPower {
    @JsonProperty("id")
    private int id;

    @JsonProperty("t")
    private int year;

    @JsonProperty("v")
    private double planPower;

    public static PlanPower fromEntity(PlanPowerEntity entity) {
        PlanPower model = new PlanPower();
        model.setId(entity.getId());
        model.setYear(entity.getYear());
        model.setPlanPower(entity.getPlanPower());
        return model;
    }

    public PlanPowerEntity toEntity() {
        PlanPowerEntity entity = new PlanPowerEntity();
        entity.setId(id);
        entity.setYear(year);
        entity.setPlanPower(planPower);

        return entity;
    }
}

