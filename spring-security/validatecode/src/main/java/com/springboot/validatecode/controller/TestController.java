package com.springboot.validatecode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello";
    }

}
