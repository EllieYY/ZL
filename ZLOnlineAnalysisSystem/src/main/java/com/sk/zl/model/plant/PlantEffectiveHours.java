package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : 机组月利用小时数 = 月发电量 / 装机容量
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
@NoArgsConstructor
public class PlantEffectiveHours  implements Comparable<PlantEffectiveHours> {
    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private Double hours;

    public PlantEffectiveHours(String name, double hours) {
        this.name = name;
        this.hours = hours;
    }

    @Override
    public int compareTo(PlantEffectiveHours o) {
        return this.getHours().compareTo(o.getHours());
    }

    public static PlantEffectiveHours fromModel(Plant model) {
        PlantEffectiveHours subModel = new PlantEffectiveHours();

        subModel.setName(model.getName());
        double hours = model.getGenCapacity() / model.getCapacity();
        subModel.setHours(hours);

        return subModel;
    }

}
