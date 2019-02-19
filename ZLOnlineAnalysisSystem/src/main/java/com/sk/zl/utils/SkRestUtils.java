package com.sk.zl.utils;

import com.sk.zl.model.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Service
public class SkRestUtils {

    @Autowired
    private RestTemplate restTemplate;







    public HttpResult get(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return new HttpResult(responseEntity.getStatusCodeValue(),
                responseEntity.getBody());
    }





//    /**
//     * @Author: Ellie
//     * @Description: get请求，查询字符串是在URL中发送的
//     * @Params: [url]
//     * @Return: java.lang.String
//     */
//    public String get(String url) {
//        ResponseEntity<String>
//
//    }

//    /**
//     * @Author: Ellie
//     * @Description: post请求，不组织body。
//     * @Params: [url]
//     * @Return: java.lang.String
//     */
//    public String post(String url) {
//
//    }
//
//    /**
//     * @Author: Ellie
//     * @Description: post请求，发送文件
//     * @Params: [uri, maps, fileList]
//     * @Return: java.lang.String
//     */
//    public String post(String uri, Map<String, String> maps, List<File> fileList) {
//
//    }
//
//    /**
//     * @Author: Ellie
//     * @Description: post请求，参数格式为key1=value1&key2=value2
//     * @Params: [uri, params]
//     * @Return: java.lang.String
//     */
//    public String post(String uri, String params) {
//
//    }
//
//    /**
//     * @Author: Ellie
//     * @Description: post请求，参数为键值对
//     * @Params: [uri, maps]
//     * @Return: java.lang.String
//     */
//    public String post(String uri, Map<String, String> maps) {
//
//    }
//
//    /**
//     * @Author: Ellie
//     * @Description: post请求，数据格式为JSON
//     * @Params: [uri, jsonStr]
//     * @Return: java.lang.String
//     */
//    public String postJson(String uri, String jsonStr) {
//
//    }
//
//    /**
//     * @Author: Ellie
//     * @Description: post请求，数据格式为xml
//     * @Params: [uri, xmlStr]
//     * @Return: java.lang.String
//     */
//    public String postXml(String uri, String xmlStr) {
//
//    }
}