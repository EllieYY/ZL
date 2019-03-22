package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.meter.MeterCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description :
 * @Author : Ellie
 * @Date : 2019/3/6
 */
@Data
@NoArgsConstructor
public class ReMeterCode {
    @JsonProperty("time")
    private Date time;

    @JsonProperty("meters")
    private List<MeterCode> meterCodes;
}
