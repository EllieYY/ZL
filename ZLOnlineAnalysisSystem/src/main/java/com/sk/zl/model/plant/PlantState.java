package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 机组检修状态
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
public class PlantState {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("state")
    private Byte state;

    public static PlantState fromModel(Plant model) {
        PlantState subModel = new PlantState();
        subModel.setId(model.getId());
        subModel.setName(model.getName());
        subModel.setState(model.getMaintaining());
        return subModel;
    }
}
