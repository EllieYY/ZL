package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : SKdb的rest接口中对返回值进行了包装。
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
@NoArgsConstructor
public class PointsInfoWrap {
    @JsonProperty("iret")
    private int iret;

    @JsonProperty("any")
    List<PointInfo> pointInfoList;
}
