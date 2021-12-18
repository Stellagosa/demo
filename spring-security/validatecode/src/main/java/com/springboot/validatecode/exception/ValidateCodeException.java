package com.springboot.validatecode.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -2425048757248141613L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
