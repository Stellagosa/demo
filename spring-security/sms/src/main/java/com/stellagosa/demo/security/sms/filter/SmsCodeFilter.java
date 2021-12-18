package com.stellagosa.demo.security.sms.filter;

import com.stellagosa.demo.security.sms.Handler.LoginAuthenticationFailureHandler;
import com.stellagosa.demo.security.sms.entity.SmsCode;
import com.stellagosa.demo.security.sms.exception.ValidateCodeException;
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
public class SmsCodeFilter extends OncePerRequestFilter {

    private final LoginAuthenticationFailureHandler failureHandler;
    public SmsCodeFilter(LoginAuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase(request.getRequestURI(), "/login/mobile") && StringUtils
                .equalsIgnoreCase(request.getMethod(), "POST")) {
            try {
                validateCode(request);
            } catch (ValidateCodeException e) {
                failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateCode(HttpServletRequest request) throws ServletRequestBindingException {

        // 表单中的电话号码
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        // 表单中的验证码
        String codeInParam = ServletRequestUtils.getStringParameter(request, "smsCode");
        //
        SmsCode codeInSession = (SmsCode) request.getSession().getAttribute("smsCode" + mobile);



        if (codeInParam == null) {
            throw new ValidateCodeException("验证码不能为空！");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException("验证码不存在！");
        }

        if (codeInSession.isExpire()) {
            throw new ValidateCodeException("验证码已过期！");
        }

        if (!StringUtils.equalsIgnoreCase(codeInParam, codeInSession.getCode())) {
            throw new ValidateCodeException("验证码错误！");
        }

        // 移除session
        request.getSession().removeAttribute("SmsCode" + mobile);
    }
}
