package com.stellagosa.demo.webSocket.chatroom.config;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: Stellagosa
 * @Date: 2022/2/8 17:01
 * @Description: http登录拦截
 */
@WebFilter(urlPatterns = "/**")
@Component
public class LoginFilter implements Filter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final UserOnlineManager userOnlineManager;

    public LoginFilter(UserOnlineManager userOnlineManager) {
        this.userOnlineManager = userOnlineManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String method = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();

        // 资源文件放过
        if (antPathMatcher.match("/index.html", requestURI)
                || antPathMatcher.match("/webjars/**", requestURI)
                || antPathMatcher.match("/script.js", requestURI)
                || antPathMatcher.match("/favicon.ico", requestURI)
                || antPathMatcher.match("/main.css", requestURI)) {
            chain.doFilter(request, response);
        }

        // 登录请求放过
        else if (method.equalsIgnoreCase("POST") && antPathMatcher.match("/login", requestURI)) {
            chain.doFilter(request, response);
        }
        // 登录请求放过
        else if (antPathMatcher.match("/portfolio/**", requestURI)) {
            chain.doFilter(request, response);
        } else {
            String token = httpServletRequest.getHeader("token");
            User user = userOnlineManager.search(token);
            if (user.getUsername() != null) {
                chain.doFilter(request, response);
            }
        }
    }
}
