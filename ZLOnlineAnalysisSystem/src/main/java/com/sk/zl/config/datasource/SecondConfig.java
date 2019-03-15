package com.sk.zl.config.datasource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @Description : sk实时数据库故障信息所在的库
 * @Author : Ellie
 * @Date : 2019/3/13
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryAlarm",
        transactionManagerRef = "transactionManagerAlarm",
        basePackages = {"com.sk.zl.dao.preview"})
public class SecondConfig {
    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "entityManagerAlarm")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryAlarm(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryAlarm")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryAlarm(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondDataSource)
                .properties(getVendorProperties())
                .packages("com.sk.zl.entity.skalarm")
                .persistenceUnit("alarmPersistenceUnit")
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "transactionManagerAlarm")
    PlatformTransactionManager transactionManagerAlarm(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryAlarm(builder).getObject());
    }


//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryAlarm() {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(alarmDataSource());
//        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        factoryBean.setPackagesToScan("com.sk.zl.entity.akalarm");
//        return factoryBean;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManagerAlarm() {
//        return new JpaTransactionManager(entityManagerFactoryAlarm().getObject());
//    }
//
//
//    @Bean
//    @ConfigurationProperties(prefix="spring.datasource.skalarm")
//    public DataSource alarmDataSource() {
//        //通过DataSourceBuilder构建数据源
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
}
