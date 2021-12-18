package com.stellagosa.demo.security.rememberme.filter;

import com.stellagosa.demo.security.rememberme.Handler.LoginAuthenticationFailureHandler;
import com.stellagosa.demo.security.rememberme.exception.ValidateCodeException;
import com.stellagosa.demo.security.rememberme.utils.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final LoginAuthenticationFailureHandler handler;

    public ValidateCodeFilter(LoginAuthenticationFailureHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase("/login", request.getRequestURI()) && StringUtils
                .equalsIgnoreCase("POST", request.getMethod())) {
            try {
                validateCode(request);
            } catch (ValidateCodeException exception) {
                handler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) request.getSession().getAttribute("imageCode");
        String codeInRequest = ServletRequestUtils.getStringParameter(request, "imageCode");

        System.out.println(codeInRequest);
        System.out.println(codeInSession.getCode());

        if (codeInRequest == null) {
            throw new ValidateCodeException("验证码不存在！");
        }
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空！");
        }
        if (codeInSession.isExpired()) {
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(codeInRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不正确！");
        }

    }
}
