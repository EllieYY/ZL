package com.sk.zl.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sk.zl.model.HttpResult;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/19
 */
@RestController
public class SkRestController {

    @Autowired
    private SkRestUtils service;

    private String m_user = "admin";
    private String m_password = "123456";
    private HttpHost m_serverHost = new HttpHost("192.168.10.99", 80, "http");
    private String m_token = "";

    @RequestMapping("/index")
    public String indexHTML() {
        System.out.println(service.get("http://www.baidu.com"));
        return "index";
    }

    @RequestMapping("/check")
    public String check() {
        checkAuthority(m_user, m_password);
        return m_token;
    }


    /**
     * @ Author: Ellie
     * @ Description: 用户认证
     * @ Params: [user, password]用户名、密码
     * @ Return: boolean
     */
    public boolean checkAuthority(String user, String password) {
        boolean hasAuthority = false;

        String dir = "/cgi-bin/login.fcg?method=jaction&user=" + user
                + "&password=" + CalculateMd5Hash(password);
        String url = m_serverHost.toURI() + dir;
        HttpResult responseContent = service.get(url);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseContent.getBody());
            m_token = node.findValue("token").asText();
            hasAuthority = true;
        } catch (Exception e) {
            m_token = "";
        }

        return hasAuthority;
    }

    /**
     * @ Author: Ellie
     * @ Description: MD5加密
     * @ Params: [content]
     * @ Return: java.lang.String
     */
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


//    @RequestMapping("/http")
//    @ResponseBody
//    public String getHttp() {
//        System.out.println(service.doGet("http://www.baidu.com"));
//        return "nice";
//    }
}
