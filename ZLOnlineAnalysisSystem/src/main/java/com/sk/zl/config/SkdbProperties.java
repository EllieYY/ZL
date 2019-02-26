package com.sk.zl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description : 数据库的基础连接信息
 * @Author : Ellie
 * @Date : 2019/2/20
 */
@Configuration
@ConfigurationProperties(prefix = "skdb")
@Data
public class SkdbProperties {
    String uri;
    String userName;
    String passWord;
}
