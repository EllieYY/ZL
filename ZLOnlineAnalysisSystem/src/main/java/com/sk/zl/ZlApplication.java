package com.sk.zl;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;


@SpringBootApplication
public class ZlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZlApplication.class, args);
	}

//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//		yaml.setResources(new FileSystemResource("config/point.yml"), new ClassPathResource("point.yml"));
//		configurer.setProperties(yaml.getObject());
//        configurer.setIgnoreResourceNotFound(true);
//		return configurer;
//	}

//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//		yaml.setResources(new ClassPathResource("plantAnalogPoints.yml"), new FileSystemResource("config/plantAnalogPoints.yml"));
//		configurer.setProperties(yaml.getObject());
//		return configurer;
//	}
}

