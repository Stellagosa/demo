package com.stellagosa.demo.security.sms.controller;

import com.stellagosa.demo.security.sms.entity.SmsCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class ValidateCodeController {

    @GetMapping("/code/sms")
    public String createSmsCode(HttpServletRequest request, String mobile) {
        SmsCode smsCode = createCode();
        request.getSession().setAttribute("smsCode" + mobile, smsCode);
        System.out.println(mobile + ": 你的短信验证码是：" + smsCode.getCode() + ",有效期为60秒");
        return "手机短信已发送";
    }

    // 100,000 - 999,999
    private SmsCode createCode() {
        Random random = new Random();
        int num = 100000 + random.nextInt(899999);
        return new SmsCode(StringUtils.toString(num), 60);
    }

}
