package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description : 机组发电量分析的时间段
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
@NoArgsConstructor
public class ReTimeSlots {
    @JsonProperty("fTimeFrom")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sTime1;

    @JsonProperty("fTimeTo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eTime1;

    @JsonProperty("sTimeFrom")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sTime2;

    @JsonProperty("sTimeTo")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eTime2;

    @Override
    public String toString() {
        return "ReTimeSlots{" +
                "sTime1=" + sTime1 +
                ", eTime1=" + eTime1 +
                ", sTime2=" + sTime2 +
                ", eTime2=" + eTime2 +
                '}';
    }
}
