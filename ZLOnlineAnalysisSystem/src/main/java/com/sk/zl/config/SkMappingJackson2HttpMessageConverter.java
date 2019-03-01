package com.sk.zl.config;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/3/1
 */
public class SkMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public SkMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("text/json"));
        setSupportedMediaTypes(mediaTypes);
    }
}
