package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 机组模拟量查询参数
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Data
public class RePlantAnalogs {
    /** 机组id， 值为0表示获取所有机组 */
    @JsonProperty("id")
    private int id;

    /** type = 1 表示只看报警 */
    @JsonProperty("type")
    private int warnOn;

    /** 当前页数 */
    @JsonProperty("pageNo")
    private int pageNo;

    /** 分页条数 */
    @JsonProperty("pageRows")
    private int pageRows;
}
