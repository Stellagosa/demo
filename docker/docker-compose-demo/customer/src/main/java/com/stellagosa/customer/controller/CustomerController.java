package com.stellagosa.customer.controller;

import com.stellagosa.customer.provider.DemoProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final DemoProvider demoProvider;

    public CustomerController(DemoProvider demoProvider) {
        this.demoProvider = demoProvider;
    }

    @GetMapping("/hello")
    public String getHello() {
        return demoProvider.hello();
    }

}
