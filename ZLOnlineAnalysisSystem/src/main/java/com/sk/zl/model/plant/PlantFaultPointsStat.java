package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 机组故障点个数统计结果
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class PlantFaultPointsStat {
    @JsonProperty("id")
    private int id;

//    @JsonProperty("name")
//    private String name;

    /** 处于故障状态测点个数 */
    @JsonProperty("fault")
    private int faultPointNum;

    /** 系统测点个数 */
    @JsonProperty("system")
    private int systemPointNum;

    public PlantFaultPointsStat(int id, int faultPointNum, int systemPointNum) {
        this.id = id;
        this.faultPointNum = faultPointNum;
        this.systemPointNum = systemPointNum;
    }
}
