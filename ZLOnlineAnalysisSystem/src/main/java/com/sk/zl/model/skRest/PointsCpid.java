package com.sk.zl.model.skRest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : 用来查询point信息的cpid集合
 * @Author : Ellie
 * @Date : 2019/2/28
 */
@Data
@NoArgsConstructor
public class PointsCpid {
    @JsonProperty("cpid")
    List<String> cpids;

    @JsonIgnore
    int id;
}
