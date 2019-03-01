package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description :点的信息
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
public class PointInfo {
    @JsonProperty("ipid")
    private long ipid;

    @JsonProperty("time")
    private Date time;

    @JsonProperty("value")
    private double value;

    @JsonProperty("status")
    private int status;

    public PointInfo() {
    }


    @Override
    public String toString() {
        return "PointInfo{" +
                "ipid=" + ipid +
                ", time=" + time +
                ", value=" + value +
                ", status=" + status +
                '}';
    }
}
