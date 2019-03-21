package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : 包装类
 * @Author : Ellie
 * @Date : 2019/3/20
 */
@Data
@NoArgsConstructor
public class PointDetailWrap {
    @JsonProperty("iret")
    private int iret;

    @JsonProperty("any")
    private List<PointDetail> data;
}
