package com.stellagosa.demo.spring_cloud.hystrix.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 17:11
 * @Description:
 */
@Configuration
public class Config {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
