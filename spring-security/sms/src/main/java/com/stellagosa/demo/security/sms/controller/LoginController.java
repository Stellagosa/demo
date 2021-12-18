package com.stellagosa.demo.security.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/successPage")
    public String success() {
        return "successPage";
    }

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello";
    }
}
