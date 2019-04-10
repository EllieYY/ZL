package com.sk.zl.config.skdb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
    private int accidentKindId;
    private int faultKindId;
}
