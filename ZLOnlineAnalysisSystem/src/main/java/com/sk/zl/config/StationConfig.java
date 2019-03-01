package com.sk.zl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 发电厂的配置信息
 * @Author : Ellie
 * @Date : 2019/3/1
 */
@Configuration
@ConfigurationProperties(prefix = "station")
@Data
public class StationConfig {
    private int genCapacityMeterGroup;
    private int onGridMeterGroup;
}
