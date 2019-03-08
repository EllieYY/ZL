package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 机组开停机参数
 * @Author : Ellie
 * @Date : 2019/3/8
 */
@Data
public class PlantRunningTimeAnalysis {
    /** 机组名称 */
    @JsonProperty("name")
    private String name;

    /** 状态类型 */
    @JsonProperty("state")
    private int state;

    /** 开机时长 */
    @JsonProperty("duration")
    private long runningTime;

    /** 开机时间 */
    @JsonProperty("sTime")
    private Date onTime;

    /** 停机时间 */
    @JsonProperty("eTime")
    private Date offTime;

    /** 开停机次数 */
    @JsonProperty("count")
    private int count;
}
