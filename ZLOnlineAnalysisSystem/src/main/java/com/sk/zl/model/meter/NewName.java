package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : 修改名称model
 * @Author : Ellie
 * @Date : 2019/4/4
 */
@Data
public class NewName {
    @JsonProperty("id")
    private int id;

    @JsonProperty("newName")
    private String newName;
}
