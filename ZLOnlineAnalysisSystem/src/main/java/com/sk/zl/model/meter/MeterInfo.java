package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.MeterEntity;
import lombok.Data;

/**
 * @Description : 电表基本信息
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
public class MeterInfo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("gname")
    private String parentName;
    @JsonProperty("gid")
    private int parentId;

    public static MeterInfo fromEntity(MeterEntity entity) {
        MeterInfo model = new MeterInfo();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setParentId(entity.getGroup().getId());
        model.setParentName(entity.getGroup().getName());
        return model;
    }
}
