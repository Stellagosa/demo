package com.stellagosa.demo.security.permission.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String adminAuthorize() {
        return "你拥有 admin 权限,可以查看";
    }

}
