package com.sk.zl.config.base;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/28
 */
//public class YamlPropertyLoaderFactory implements PropertySourceFactory {
//    @Override
//    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
//
//        List<PropertySource<?>> sources = name != null ? new YamlPropertySourceLoader().load(name, encodedResource.getResource()) : new YamlPropertySourceLoader().load(
//                getNameForResource(encodedResource.getResource()), encodedResource.getResource());
//        if (sources.size() == 0) {
//            return null;
//        }
//
//        return sources.get(0);
//    }
//
//    private static String getNameForResource(Resource resource) {
//        String name = resource.getDescription();
//        if (!StringUtils.hasText(name)) {
//            name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
//        }
//        return name;
//    }
//}
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null) {
            return super.createPropertySource(name, resource);
        }
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        if (sources.size() == 0) {
            return super.createPropertySource(name, resource);
        }

        return sources.get(0);
    }
}
