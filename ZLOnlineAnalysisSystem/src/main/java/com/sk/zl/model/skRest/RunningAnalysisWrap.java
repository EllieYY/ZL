package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.zl.model.request.ReRunningTimeAnalysis;
import lombok.Data;


/**
 * @Description : skd返回开停机分析结果包装类
 * @Author : Ellie
 * @Date : 2019/3/19
 */
@Data
public class RunningAnalysisWrap {
    @JsonProperty("any")
    private ReRunningTimeAnalysis param;
}
