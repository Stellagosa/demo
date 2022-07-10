package com.stellagosa.demo.sas.client_registration_endpoint.entity;

import lombok.Data;

import java.time.Instant;

/**
 * @Author: Stellagosa
 * @Date: 2022/6/30 14:14
 * @Description:
 */
@Data
public class Client {
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    private String clientAuthenticationMethods;
    private String authorizationGrantTypes;
    private String redirectUris;
    private String scopes;
    private String clientSettings;
    private String tokenSettings;

}
