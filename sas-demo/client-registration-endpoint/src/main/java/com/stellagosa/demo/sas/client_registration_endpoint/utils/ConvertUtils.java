package com.stellagosa.demo.sas.client_registration_endpoint.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 15:28
 * @Description:
 */
public class ConvertUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.registerModules(securityModules);
    }
    public static AuthorizationGrantType string2AuthorizationGrantType(String type) {
        Assert.hasText(type, "type cannot be empty");
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(type)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(type)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.JWT_BEARER.getValue().equals(type)) {
            return AuthorizationGrantType.JWT_BEARER;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(type)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        } else if (AuthorizationGrantType.PASSWORD.getValue().equals(type)) {
            return AuthorizationGrantType.PASSWORD;
        } else {
            return new AuthorizationGrantType(type); // 自定义类型
        }
    }

    public static ClientAuthenticationMethod string2ClientAuthenticationMethod(String method) {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(method)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(method)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_JWT.getValue().equals(method)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_JWT;
        } else if (ClientAuthenticationMethod.PRIVATE_KEY_JWT.getValue().equals(method)) {
            return ClientAuthenticationMethod.PRIVATE_KEY_JWT;
        } else if (ClientAuthenticationMethod.NONE.getValue().equals(method)) {
            return ClientAuthenticationMethod.NONE;
        } else {
            return new ClientAuthenticationMethod(method); // 自定义
        }
    }

    public static Map<String, Object> readMap(String str) {
        try {
            return objectMapper.readValue(str, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String writeMap(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T string2Object(String str, TypeReference<T> t) {
        try {
            return objectMapper.readValue(str, t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String object2String(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
