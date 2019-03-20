package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description : 一览表查询和报警查询共用参数
 * @Author : Ellie
 * @Date : 2019/3/7
 */
@Data
public class ReDataPreview {
    /** 要查询的机组id列表 */
    @JsonProperty("id")
    private List<Integer> ids;

    /** 数据类型：0-全部 1-事故 2-故障 3-状变 */
    /** 数据类型：0-全部 1-上限 2-下限 */
    @JsonProperty("dataType")
    private int dataType;

    /** 关键字，用来过滤搜索结果的 */
    @JsonProperty("filter")
    private String keyword;

    /** 开始时间 */
    @JsonProperty("startTime")
    private Date startTime;

    /** 结束时间 */
    @JsonProperty("endTime")
    private Date endTime;

    /** 当前页数 */
    @JsonProperty("pageNo")
    private int pageNo;

    /** 分页条数 */
    @JsonProperty("pageRows")
    private int pageRows;

}
