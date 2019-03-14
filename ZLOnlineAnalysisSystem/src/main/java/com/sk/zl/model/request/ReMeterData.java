package com.sk.zl.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description : 查询电表基础数据参数
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Data
public class ReMeterData {
    @JsonProperty("groupId")
    private int groupId;

    @JsonProperty("date")
    private Date date;
}
