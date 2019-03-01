package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.plant.PlantState;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
@NoArgsConstructor
public class RePlantMaintaing {
    @JsonProperty("type")
    private String type;

    @JsonProperty("plants")
    List<PlantState> plantStates;
}
