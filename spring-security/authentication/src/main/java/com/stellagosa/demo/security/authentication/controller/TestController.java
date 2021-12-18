package com.stellagosa.demo.security.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ellaend
 * @date 2020/11/6 11:33
 * version 1.0
 */
@Controller
public class TestController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @GetMapping("/login.html")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello";
    }

    @GetMapping("/abc.html")
    @ResponseBody
    public Object abc() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/abcd.html")
    @ResponseBody
    public Object abcd(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/authentication/require")
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(redirectUrl, "html")) {
                redirectStrategy.sendRedirect(request, response, "/login.html");
//                response.sendRedirect("/login.html");
            }
        }
        return "访问的资源需要身份认证！";
    }

}
