package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.request.ReDataAnalysis;
import lombok.Data;


/**
 * @Description : skd返回开停机分析参数
 * @Author : Ellie
 * @Date : 2019/3/19
 */
@Data
public class DataAnalysisParam {
    @JsonProperty("any")
    private ReDataAnalysis param;
}
