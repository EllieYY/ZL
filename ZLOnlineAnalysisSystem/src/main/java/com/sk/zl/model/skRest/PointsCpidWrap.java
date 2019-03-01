package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : 匹配SKdb的rest接口，包一层any.
 * @Author : Ellie
 * @Date : 2019/2/27
 */
@Data
@NoArgsConstructor
public class PointsCpidWrap {
    @JsonProperty("any")
    PointsCpid points;
}
