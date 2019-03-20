package com.sk.zl.model.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description : 一览表查询和报警查询的分页信息
 * @Author : Ellie
 * @Date : 2019/3/20
 */
@Data
public class PagePlantDataPreview {
    @JsonProperty("total")
    private long totalNum;

    @JsonProperty("data")
    private List<PlantDataPreview> data;
}
