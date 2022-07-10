package com.stellagosa.demo.sas.client_registration_endpoint.entity;

import lombok.Data;

import java.time.Instant;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 10:12
 * @Description:
 */
@Data
public class Authorization {

    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    private String attributes;
    private String state;

    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;

    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    private String accessTokenMetadata;
    private String accessTokenType;
    private String accessTokenScopes;

    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    private String refreshTokenMetadata;

    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    private String oidcIdTokenMetadata;
    private String oidcIdTokenClaims;
}
