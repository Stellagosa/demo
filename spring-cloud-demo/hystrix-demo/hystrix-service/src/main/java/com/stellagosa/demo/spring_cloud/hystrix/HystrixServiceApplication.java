package com.stellagosa.demo.spring_cloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 17:11
 * @Description:
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class HystrixServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixServiceApplication.class, args);
    }
}
