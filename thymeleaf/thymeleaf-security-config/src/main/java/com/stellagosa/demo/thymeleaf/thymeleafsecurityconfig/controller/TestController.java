package com.stellagosa.demo.thymeleaf.thymeleafsecurityconfig.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Stellagosa
 * @Date: 2022/9/2 17:02
 * @Description:
 */
@Controller
public class TestController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
