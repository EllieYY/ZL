package com.sk.zl.config.skdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Data
@Configuration
@PropertySource("classpath:plantAnalogPoints.yml")
@ConfigurationProperties(prefix = "analog")
public class PlantsAnalog {
    @JsonProperty("plants")
    private List<AnalogPoints> plants;

    @Override
    public String toString() {
        return "PlantsAnalog{" +
                "plants=" + plants +
                '}';
    }
}
