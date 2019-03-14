package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.zheling.PlantEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : 机组状态和测点信息：状态、有功、无功、导叶开度
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
@NoArgsConstructor
public class PlantPointSnapshot {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    /** 状态：检修-1 正常-0 */
    @JsonProperty("state")
    private int state;

    /** 有功功率 */
    @JsonProperty("activePower")
    private double activePower;

    /** 无功功率 */
    @JsonProperty("reactivePower")
    private double reactivePower;

    /** 导叶开度 */
    @JsonProperty("vaneOpen")
    private double guideVaneOpening;


    public static PlantPointSnapshot fromEntity(PlantEntity entity) {
        PlantPointSnapshot model = new PlantPointSnapshot();
        model.setState(entity.getMaintaining());
        model.setId(entity.getId());
        model.setName(entity.getName());
        return model;
    }

    public static PlantPointSnapshot fromModel(Plant model) {
        PlantPointSnapshot subModel = new PlantPointSnapshot();

        subModel.setState(model.getMaintaining());
        subModel.setId(model.getId());
        subModel.setName(model.getName());
        subModel.setActivePower(model.getActivePower());
        subModel.setReactivePower(model.getReactivePower());
        subModel.setGuideVaneOpening(model.getGuideVaneOpening());

        return subModel;
    }

}
