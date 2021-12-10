package com.stellagosa.demo.spring_cloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/10 16:09
 * @Description:
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaSecurityServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSecurityServerApplication.class, args);
    }
}
