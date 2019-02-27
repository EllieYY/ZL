package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description : 测点
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
public class Points {
    @JsonProperty("id")
    List<Long> id;
}
