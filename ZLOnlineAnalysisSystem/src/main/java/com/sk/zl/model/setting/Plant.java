package com.sk.zl.model.setting;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.PlantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @JsonProperty("id")
    private int id;

    @JsonProperty("state")
    private Byte state;

    public static Plant fromEntity(PlantEntity entity)
    {
        Plant model = new Plant();
        model.setId(entity.getId());
        model.setState(entity.getMaintaining());
        return model;
    }

    public PlantEntity toEntity()
    {
        PlantEntity entity = new PlantEntity();
        entity.setId(id);
        entity.setMaintaining(state);
        return entity;
    }
}
