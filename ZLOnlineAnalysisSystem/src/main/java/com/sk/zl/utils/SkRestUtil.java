package com.sk.zl.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.zl.config.SkdbProperties;
import com.sk.zl.model.meter.MeterRate;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.result.HttpResult;
import com.sk.zl.model.skRest.Points;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.Deflater;

/**
 * @Description : SK数据库连接的工具类，处理与数据库耦合较强的信息。
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Service
public class SkRestUtil {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SkdbProperties skdbProperties;

    private String token = "";


    /** 读取机组测点的信息 */
    public PlantPointSnapshot getPlantPoint() {
        String dir = "/cgi-bin/nowval.rsp?method=mGet";

        //#1 组post的body部分


        //#2 对body进行压缩


        //#3 带上token和压缩参数


        //#4 对返回参数进行判断，看是否需要重更新进行用户认证
        // ----- 需要加上重试逻辑。


        //#5 对返回值进行解析

        return null;
    }

    /** 获取当前值 */
    private String getNowValue(Points points) {
        String dir = "/cgi-bin/nowval.rsp?method=mGet";

        //#1 组post的body部分
        ObjectMapper mapper = new ObjectMapper();
        String ids = "";
        try {
            ids = mapper.writeValueAsString(points);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //#2 对body进行压缩
        byte[] originContent = ("id=" + ids).getBytes();
        byte[] requestContent = compress(originContent);

        //#3 组织url：带上token和压缩参数
        if (token.isEmpty()) {
            checkAuthority(skdbProperties.getUserName(), skdbProperties.getPassWord());
        }

        //#4 发送post请求




        return null;
    }


    /**
     * @Author: Ellie
     * @Description: get请求，查询字符串是在URL中发送的
     * @Params: [url]
     * @Return: java.lang.String
     */
    public HttpResult get(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return new HttpResult(responseEntity.getStatusCodeValue(),
                responseEntity.getBody());
    }


    /** 用户认证 */
    public boolean checkAuthority(String user, String password) {
        boolean hasAuthority = false;

        String dir = "/cgi-bin/login.fcg?method=jaction&user=" + user
                + "&password=" + CalculateMd5Hash(password);
        String url = skdbProperties.getUri() + dir;
        HttpResult responseContent = get(url);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseContent.getBody());
            token = node.findValue("token").asText();
            hasAuthority = true;
        } catch (Exception e) {
            token = "";
        }

        return hasAuthority;
    }

    /** MD5加密 */
    private String CalculateMd5Hash(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte[] b = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 数据压缩 */
    private byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }




//    /**
//     * @Author: Ellie
//     * @Description: post请求，数据格式为JSON
//     * @Params: [uri, jsonStr]
//     * @Return: java.lang.String
//     */
//    public HttpResult postJson(String uri, String jsonStr) {
//        HttpHeaders headers=new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(jsonStr, headers);
//        ResponseEntity<String> entity = restTemplate.postForEntity(uri, request, String.class);
//
//        return new HttpResult(
//                entity.getStatusCodeValue(),
//                entity.getBody());
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

//    /**
//     * @Author: Ellie
//     * @Description: post请求，发送文件
//     * @Params: [uri, maps, fileList]
//     * @Return: java.lang.String
//     */
//    public String post(String uri, Map<String, String> maps, List<File> fileList) {
//        HttpHeaders headers=new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setContentType(MediaType.APPLICATION_FROM_URLENCODED);
//        MultiValueMap<String,String> map=new LinkedMultiValueMap<String,String>();
//        map.add("title",title);
//        map.add("userid",toUserId);
//        HttpEntity<MultiValueMap<String,String>> reqest=new HttpEntity<MultiValueMap<String,String>>(map,headers);
//        ResponseEntity<String> response=restTemplate.postForEntity(url,request,String.class);
//    }

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