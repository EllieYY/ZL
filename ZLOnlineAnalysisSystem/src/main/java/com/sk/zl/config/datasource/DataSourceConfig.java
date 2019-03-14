package com.sk.zl.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Description : 数据源连接信息配置
 * @Author : Ellie
 * @Date : 2019/3/14
 */
@Configuration
public class DataSourceConfig {
    /** 主数据源 */
    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.zheling")
    public DataSource primaryDataSource(){
        return new DruidDataSource();
    }

    @Bean(name = "secondDataSource")
    @Qualifier("secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.skalarm")
    public DataSource secondDataSource(){
        return new DruidDataSource();
    }
}
