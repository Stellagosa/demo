package com.stellagosa.demo.security.authentication.security.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: stellagosa
 * @Date: 2020/11/6 11:11
 */
@RestController
public class TestController {

    @GetMapping("/")
    public String hello() {
        return "Hello Spring Security!";
    }
}
