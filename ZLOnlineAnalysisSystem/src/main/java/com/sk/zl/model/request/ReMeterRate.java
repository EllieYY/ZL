package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.meter.MeterRate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
@NoArgsConstructor
public class ReMeterRate {
    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private int meterId;

    @JsonProperty("rate")
    private List<MeterRate> rates;

    @Override
    public String toString() {
        return "ReMeterRate{" +
                "type='" + type + '\'' +
                ", meterId=" + meterId +
                ", rates=" + rates +
                '}';
    }
}
