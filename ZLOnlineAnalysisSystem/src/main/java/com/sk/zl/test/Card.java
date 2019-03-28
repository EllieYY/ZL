package com.sk.zl.test;

import lombok.Data;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/28
 */
@Data
public class Card {
    private String name;
    private String code;

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
