package com.sk.zl.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @Description : 基础信息和电量计算信息存储的数据库
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryZL",
        transactionManagerRef = "transactionManagerZL",
        basePackages = {"com.sk.zl.dao.meter", "com.sk.zl.dao.setting"})
public class PrimaryConfig {
    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "entityManagerZL")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryZL(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryZL")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryZL(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource)
                .packages("com.sk.zl.entity.zheling")
                .persistenceUnit("zlPersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "transactionManagerZL")
    public PlatformTransactionManager transactionManagerZL(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryZL(builder).getObject());
    }


//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryZL() {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(zlDataSource());
//        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        // 此处指定第一数据源对应实体类包路径
//        factoryBean.setPackagesToScan("com.sk.zl.entity.zheling");
//        return factoryBean;
//    }
//
//
//    @Bean
//    public PlatformTransactionManager transactionManagerZL() {
//        return new JpaTransactionManager(entityManagerFactoryZL().getObject());
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource.zheling")
//    public DataSource zlDataSource() {
//        //通过DataSourceBuilder构建数据源
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
}
