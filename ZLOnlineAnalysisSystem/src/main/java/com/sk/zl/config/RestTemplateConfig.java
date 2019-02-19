package com.sk.zl.config;

import com.sk.zl.config.HttpPoolProperties;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Description : TODO
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    private HttpPoolProperties httpPoolProperties;

    /**
     * @Author: Ellie
     * @Description: 定义连接池
     * @Params: []
     * @Return: org.apache.http.impl.conn.PoolingHttpClientConnectionManager
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        /** 配置同时支持http和https */
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(httpPoolProperties.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(httpPoolProperties.getDefaultMaxPerRoute());
        connectionManager.setValidateAfterInactivity(httpPoolProperties.getValidateAfterInactivity());
        return connectionManager;
    }

    /**
     * @Author: Ellie
     * @Description: 定义HttpClient工厂，实例化连接池，设置连接池管理器。
     * @Params: [httpClientConnectionManager]
     * @Return: org.apache.http.impl.client.HttpClientBuilder
     */
    @Bean("httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(
            @Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        return httpClientBuilder;
    }

    /**
     * @Author: Ellie
     * @Description: 注入连接池，获取httpClient
     * @Params: [httpClientBuilder]
     * @Return: org.apache.http.impl.client.CloseableHttpClient
     */
    @Bean
    public CloseableHttpClient getCloseableHttpClient(
            @Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
        return httpClientBuilder.build();
    }

    @Bean
    public RestTemplate restTemplate(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(clientHttpRequestFactory);
    }

    /**
     * @Author: Ellie
     * @Description: 定义requestConfig工厂，预留用于设置proxy、cookies等属性。
     * @Params: []
     * @Return: org.apache.http.client.config.RequestConfig.Builder
     */
    @Bean(name = "configBuilder")
    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(httpPoolProperties.getConnectTimeout())
                .setConnectionRequestTimeout(httpPoolProperties.getConnectionRequestTimeout())
                .setSocketTimeout(httpPoolProperties.getSocketTimeout());
    }

    /**
     * @Author: Ellie
     * @Description: 构建requestConfig对象
     * @Params: [builder]
     * @Return: org.apache.http.client.config.RequestConfig
     */
    @Bean
    public RequestConfig getRequestConfig(
            @Qualifier("configBuilder") RequestConfig.Builder builder) {
        return builder.build();
    }




//    // ================================================
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//
//        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
//        HttpMessageConverter<?> converterTarget = null;
//        for (HttpMessageConverter<?> item : converterList) {
//            if (StringHttpMessageConverter.class == item.getClass()) {
//                converterTarget = item;
//                break;
//            }
//        }
//        if (null != converterTarget) {
//            converterList.remove(converterTarget);
//        }
//        converterList.add(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
//        return restTemplate;
//    }
//
//    @Bean
//    public ClientHttpRequestFactory httpRequestFactory() {
//        return new HttpComponentsClientHttpRequestFactory(httpClient());
//    }
//
//    @Bean
//    public HttpClient httpClient() {
//        /** 配置同时支持http和https */
//        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", SSLConnectionSocketFactory.getSocketFactory())
//                .build();
//
//        /** 初始化连接管理池 */
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
//        connectionManager.setMaxTotal(httpPoolProperties.getMaxTotal());
//        connectionManager.setDefaultMaxPerRoute(httpPoolProperties.getDefaultMaxPerRoute());
//        connectionManager.setValidateAfterInactivity(httpPoolProperties.getValidateAfterInactivity());
//
//        /** 初始化request config */
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setSocketTimeout(httpPoolProperties.getSocketTimeout())
//                .setConnectTimeout(httpPoolProperties.getConnectTimeout())
//                .setConnectionRequestTimeout(httpPoolProperties.getConnectionRequestTimeout())
//                .build();
//
//        return HttpClientBuilder.create()
//                .setDefaultRequestConfig(requestConfig)
//                .setConnectionManager(connectionManager)
//                .build();
//    }
}
