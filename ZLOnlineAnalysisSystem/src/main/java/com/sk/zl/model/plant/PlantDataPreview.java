package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.entity.skalarm.HisalarmEntity;
import lombok.Data;

import javax.xml.stream.events.EndElement;
import java.util.Date;

/**
 * @Description : 一览表和报警表 --- 机组数据
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class PlantDataPreview {
    /** 机组id */
    @JsonProperty("deviceId")
    private int deviceId;

    /** 数据类型 */
    @JsonProperty("type")
    private int dataType;

    /** 测点的cpid */
    @JsonProperty("cpid")
    private String cpid;

    /** 测点名称 */
    @JsonProperty("pname")
    private String pointName;

    /** 状态数据触发时刻 */
    @JsonProperty("triggerTime")
    private Date triggerTime;

    /** 状态数据结束时刻 */
    @JsonProperty("endTime")
    private Date endTime;

    public static PlantDataPreview fromEntity(HisalarmEntity entity) {
        PlantDataPreview model = new PlantDataPreview();
        model.setDeviceId(entity.getDevid());
        model.setDataType(entity.getKindid());
        model.setCpid(entity.getPoint().getCpid());
        model.setPointName(entity.getPoint().getName());
        model.setTriggerTime(entity.getStime());
        model.setEndTime(entity.getEtime());
        return model;
    }

    @Override
    public String toString() {
        return "PlantDataPreview{" +
                "deviceId='" + deviceId + '\'' +
                ", dataType=" + dataType +
                ", cpid='" + cpid + '\'' +
                ", pointName='" + pointName + '\'' +
                ", triggerTime=" + triggerTime +
                ", endTime=" + endTime +
                '}';
    }
}
