package com.springboot.validatecode.fliter;

import com.springboot.validatecode.controller.ValidateCodeController;
import com.springboot.validatecode.entity.ImageCode;
import com.springboot.validatecode.exception.ValidateCodeException;
import com.springboot.validatecode.handler.MyAuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final MyAuthenticationFailureHandler authenticationFailureHandler;
    private final SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    public ValidateCodeFilter(MyAuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        request.getRequestURL();  http://localhost:8080/
//        request.getRequestURI();  /login
        if (StringUtils.equalsIgnoreCase("/login", request.getRequestURI()) && StringUtils
                .equalsIgnoreCase("post", request.getMethod())) {
            try {
                validateCode(new ServletWebRequest(request));
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return; //一定要结束，不然还会执行doFilter
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY_IMAGE_CODE);
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (codeInRequest == null) {
            throw new ValidateCodeException("验证码不存在！");
        }
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空！");
        }
        if (codeInSession.isExpire()) {
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!StringUtils.equalsIgnoreCase(codeInRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不正确！");
        }

        // 验证码通过，移除 session 中的验证码
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY_IMAGE_CODE);
    }
}
