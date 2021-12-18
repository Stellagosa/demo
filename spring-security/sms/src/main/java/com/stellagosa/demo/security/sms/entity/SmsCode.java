package com.stellagosa.demo.security.sms.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsCode {

    private String code;
    private LocalDateTime expireTime;

    public SmsCode(String code, int expireInt) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInt);
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
