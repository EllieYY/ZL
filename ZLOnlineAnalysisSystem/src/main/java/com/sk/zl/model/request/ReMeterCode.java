package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.meter.MeterCode;
import lombok.Data;

import java.util.List;

/**
 * @Description :
 * @Author : Ellie
 * @Date : 2019/3/6
 */
@Data
public class ReMeterCode {
    @JsonProperty("type")
    String type;

    @JsonProperty("codes")
    List<MeterCode> meterCodes;
}
