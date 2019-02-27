package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : 机组当月发电量信息
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
@NoArgsConstructor
public class PlantGenerateCapacity {
    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private double genCapacity;

    public PlantGenerateCapacity(String name, double genCapacity) {
        this.name = name;
        this.genCapacity = genCapacity;
    }

    public static PlantGenerateCapacity fromModel(Plant model) {
        PlantGenerateCapacity subModel = new PlantGenerateCapacity();
        subModel.setName(model.getName());
        subModel.setGenCapacity(model.getGenCapacity());
        return subModel;
    }

}
