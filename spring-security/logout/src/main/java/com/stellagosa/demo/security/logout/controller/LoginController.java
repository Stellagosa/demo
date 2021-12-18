package com.stellagosa.demo.security.logout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ellaend
 * @date 2020/11/9 22:09
 * version 1.0
 */
@Controller
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/session/invalid")
    @ResponseBody
    public String sessionInvalid() {
        return "Session invalid";
    }


}
