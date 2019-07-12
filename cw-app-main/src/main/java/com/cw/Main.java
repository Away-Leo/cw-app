package com.cw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 * Created by dujy on 2017-05-20.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args)
    {
        System.setProperty("cw.appName","cw-app");
        SpringApplication.run(Main.class,args);
    }

    /**
     * 设置会话超时设置
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(3600);//单位为S
            }
        };
    }
}
