package com.sk.zl.model.plant;


import com.sk.zl.entity.zheling.PlantEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description : 机组信息模型
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    /** 机组id */
    private int id;
    /** 机组名称 */
    private String name;
    /** 装机容量 */
    private Double capacity;
    /** 维修状态 */
    private Byte maintaining;
    /** 电表id */
    private int meterId;


    /** 有功功率 */
    private double activePower;
    /** 无功功率 */
    private double reactivePower;
    /** 导叶开度 */
    private double guideVaneOpening;
    /** 发电量：表示一段时间内的发电量，在不同的请求下表示不同含义的电量 */
    private double genCapacity;

    public static Plant fromEntity(PlantEntity entity)
    {
        Plant model = new Plant();

        model.setId(entity.getId());
        model.setMaintaining(entity.getMaintaining());
        model.setCapacity(entity.getCapacity());
        model.setName(entity.getName());
        model.setMeterId(entity.getId());

        return model;
    }

    public PlantState toPlantState() {
        PlantState subModel = new PlantState();
        subModel.setId(id);
        subModel.setName(name);
        subModel.setState(maintaining);
        return subModel;
    }

    public PlantPointSnapshot toPointSnapshot() {
        PlantPointSnapshot subModel = new PlantPointSnapshot();
        subModel.setId(id);
        subModel.setName(name);
        subModel.setState(maintaining);
        subModel.setActivePower(activePower);
        subModel.setReactivePower(reactivePower);
        subModel.setGuideVaneOpening(guideVaneOpening);
        return subModel;
    }
}
