package com.sk.zl.test;

import com.sk.zl.config.base.YamlPropertyLoaderFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/28
 */
@Data
@Configuration
@PropertySource(value = {"classpath:point.yml", "file:config/point.yml"}, factory = YamlPropertyLoaderFactory.class, ignoreResourceNotFound = true)
@ConfigurationProperties
public class TestPoint{
    private int id;

    private String name;

    private List<Card> cards;

    @Override
    public String toString() {
        return "TestPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}
