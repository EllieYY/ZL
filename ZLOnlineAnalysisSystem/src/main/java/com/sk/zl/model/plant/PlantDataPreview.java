package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 一览表 --- 机组数据
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class PlantDataPreview {
    /** 机组名称 */
    @JsonProperty("name")
    private String name;

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
}
