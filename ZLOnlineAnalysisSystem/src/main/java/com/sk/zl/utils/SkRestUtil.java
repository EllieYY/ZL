package com.sk.zl.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.zl.aop.excption.ServiceException;
import com.sk.zl.config.SkdbProperties;
import com.sk.zl.model.plant.PlantPointSnapshot;
import com.sk.zl.model.result.HttpResult;
import com.sk.zl.model.skRest.PointInfo;
import com.sk.zl.model.skRest.PointsCpid;
import com.sk.zl.model.skRest.PointsCpidWrap;
import com.sk.zl.model.skRest.PointsInfoWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Deflater;

/**
 * @Description : SK数据库连接的工具类，处理与数据库耦合较强的信息。
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Component
public class SkRestUtil {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SkdbProperties skdbProperties;

    private String token = "";

    private final int RETRY_TIME = 2;
    private boolean identification = false;

    public PointInfo getNowValue(String cpid) {
        List<PointInfo> points = getNowValue(Arrays.asList(cpid));
        if (points.size() < 1) {
            return null;
        }
        return points.get(0);
    }

    /** 获取当前值 */
    public List<PointInfo> getNowValue(List<String> cpids) {
        PointsCpid points = new PointsCpid();
        points.setCpids(cpids);

        PointsCpidWrap pointsCpidWrap = new PointsCpidWrap();
        pointsCpidWrap.setPoints(points);

        String url = skdbProperties.getUri() + "/cgi-bin/nowval.fcg?method=jaction";

        return getPointValue(url, pointsCpidWrap);
    }

    private List<PointInfo> getPointValue(String url, PointsCpidWrap cpidWrap) {
        //#1 组post的body部分
        ObjectMapper mapper = new ObjectMapper();
        String ids = "";
        try {
            ids = mapper.writeValueAsString(cpidWrap).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //#2 对body进行压缩
        byte[] content = ("any=" + ids).getBytes();
        byte[] requestContent = compress(content);
        url += ("&compress=" + content.length);

        //#3 发送post请求
        for (int i = 0; i < RETRY_TIME; i++) {
            // 用户认证
            if (!identification) {
                identification = checkAuthority(skdbProperties.getUserName(), skdbProperties.getPassWord());
                log.debug("get token: " + token);
                continue;
            }
            url += ("&token=" + token);

            // post请求 -
            HttpResult result = post(url, requestContent);

            if (result.getCode() == 401) {
                identification = false;
                continue;
            } else if (result.getCode() > 300) {
                throw new ServiceException("rest template error code:" + result.getCode());
            }

            try {
                PointsInfoWrap pts = mapper.readValue(result.getBody(), PointsInfoWrap.class);
                int ret = pts.getIret();
                if (ret != 0) {
                    break;
                }
                return pts.getPointInfoList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
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

    public HttpResult post(String url, byte[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON, MediaType.APPLICATION_STREAM_JSON, MediaType.TEXT_PLAIN));
        HttpEntity<byte[]> entity = new HttpEntity<byte[]>(data, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return new HttpResult(response.getStatusCodeValue(), response.getBody());
    }


    /** 用户认证 */
    private boolean checkAuthority(String user, String password) {
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