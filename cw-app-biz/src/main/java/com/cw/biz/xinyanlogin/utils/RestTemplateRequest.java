package com.cw.biz.xinyanlogin.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

/**
 * http,https请求
 *
 * @author: yingmuhuadao
 * @date: Created in 15:11 2018/12/18
 */
@Slf4j
public class RestTemplateRequest {

    private static class RestTemplateHolder
    {
        private static final RestTemplate TEMPLATE = restTemplate();
    }

    private static class HeaderHolder{
        private static final HttpHeaders HEADERS = setHeaders();
    }

    private static HttpHeaders setHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAcceptCharset(Arrays.asList(StandardCharsets.UTF_8));
        return httpHeaders;
    }

    private static RestTemplate restTemplate(){

        RestTemplate restTemplate = null;
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);
            requestFactory.setConnectTimeout(30000);
            requestFactory.setReadTimeout(30000);
            restTemplate = new RestTemplate(requestFactory);
            List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
            HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
            messageConverters.add(1,converter);
            restTemplate.setMessageConverters(messageConverters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restTemplate;
    }


    public static final RestTemplate getRestTemplate() {
        return RestTemplateHolder.TEMPLATE;
    }

    public static final HttpHeaders getHttpHeaders() {
        return HeaderHolder.HEADERS;
    }
    /**
     * 请求处理
     *
     * @param body
     * @return
     */
    public static String request(String body, String url){
        URI uri = URI.create(url);
        HttpEntity<String> httpEntity = new HttpEntity<>(body,getHttpHeaders());
        ResponseEntity<String> exchange = getRestTemplate().exchange(uri, HttpMethod.POST, httpEntity, String.class);
        return exchange.getBody();
    }
}
