package com.sk.zl.model.meter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/4/4
 */
@Data
public class ReNewName {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    public ReNewName(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
