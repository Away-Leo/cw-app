package com.cw.web.front.controller;

import com.cw.web.common.filter.CwFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 控制器注册
 * Created by dujy on 2017-05-22.
 */
@Configuration
public class FrontWebConfig {

    @Bean(name = "front_loginFilter_reg")
    public FilterRegistrationBean loginFilterRegistrationBean() {
        FilterRegistrationBean bean = new CwFilter.Builder().loginUrl("/front/login.html")
                .notNeedLoginUrls("/css/login.css","/front/parameter/findParameter.json",
                        "/front/product/findProductVersion.json",
                        "/front/product/findByCondition.json",
                        "/front/creditCard/findRecommend.json",
                        "/front/creditCard/findByCondition.json",
                        "/front/sys/getAppModule.json","/front/product/findApplyProductRecommend.json",
                        "/front/product/findRecommendProduct.json",
                        "/front/product/findProductType.json",
                        "/front/product/findApplyResult.json",
                        "/front/creditCard/findBankByPosition.json")
                .urlPatterns("/front/**").supportUserType("user").build();
        bean.setName("front_loginFilter");
        return bean;
    }
}
