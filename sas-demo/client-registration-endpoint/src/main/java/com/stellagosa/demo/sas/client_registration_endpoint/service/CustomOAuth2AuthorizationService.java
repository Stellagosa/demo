package com.stellagosa.demo.sas.client_registration_endpoint.service;

import com.stellagosa.demo.sas.client_registration_endpoint.dao.AuthorizationDao;
import com.stellagosa.demo.sas.client_registration_endpoint.entity.Authorization;
import com.stellagosa.demo.sas.client_registration_endpoint.utils.ConvertUtils;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 8:02
 * @Description:
 */
@Component
public class CustomOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final AuthorizationDao authorizationDao;

    private final CustomRegisteredClientRepository customRegisteredClientRepository;

    public CustomOAuth2AuthorizationService(AuthorizationDao authorizationDao, CustomRegisteredClientRepository customRegisteredClientRepository) {
        this.authorizationDao = authorizationDao;
        this.customRegisteredClientRepository = customRegisteredClientRepository;
    }

    @Transactional
    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "OAuth2Authorization cannot be null");

        Authorization res = authorizationDao.findById(authorization.getId());
        if (res == null) {
            authorizationDao.save(oauth2Authorization2Authorization(authorization));
        } else {
            authorizationDao.update(oauth2Authorization2Authorization(authorization));
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationDao.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return authorization2OAuth2Authorization(authorizationDao.findById(id));
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        if (tokenType == null) {
            return authorization2OAuth2Authorization(authorizationDao.findByStateOrCodeOrAccessTokenOrRefreshToken(token));
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            return authorization2OAuth2Authorization(authorizationDao.findByState(token));
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            return authorization2OAuth2Authorization(authorizationDao.findByAccessTokenValue(token));
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            return authorization2OAuth2Authorization(authorizationDao.findByAuthorizationCodeValue(token));
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            return authorization2OAuth2Authorization(authorizationDao.findByRefreshTokenValue(token));
        } else {
            return null;
        }
    }

    private Authorization oauth2Authorization2Authorization(OAuth2Authorization oauth2Authorization) {
        Authorization authorization = new Authorization();

        authorization.setId(oauth2Authorization.getId());
        authorization.setRegisteredClientId(oauth2Authorization.getRegisteredClientId());
        authorization.setPrincipalName(oauth2Authorization.getPrincipalName());
        authorization.setAuthorizationGrantType(oauth2Authorization.getAuthorizationGrantType().getValue());
        authorization.setAttributes(ConvertUtils.writeMap(oauth2Authorization.getAttributes()));
        authorization.setState(oauth2Authorization.getAttribute(OAuth2ParameterNames.STATE));

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = oauth2Authorization.getToken(OAuth2AuthorizationCode.class);
        if (authorizationCodeToken != null) {
            authorization.setAuthorizationCodeValue(authorizationCodeToken.getToken().getTokenValue());
            authorization.setAuthorizationCodeExpiresAt(authorizationCodeToken.getToken().getExpiresAt());
            authorization.setAuthorizationCodeIssuedAt(authorizationCodeToken.getToken().getIssuedAt());
            authorization.setAuthorizationCodeMetadata(ConvertUtils.writeMap(authorizationCodeToken.getMetadata()));
        }

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = oauth2Authorization.getToken(OAuth2AccessToken.class);
        if (accessToken != null) {
            authorization.setAccessTokenValue(accessToken.getToken().getTokenValue());
            authorization.setAccessTokenExpiresAt(accessToken.getToken().getExpiresAt());
            authorization.setAccessTokenIssuedAt(accessToken.getToken().getIssuedAt());
            authorization.setAccessTokenMetadata(ConvertUtils.writeMap(accessToken.getMetadata()));
            authorization.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
            authorization.setAccessTokenType(OAuth2AccessToken.TokenType.BEARER.getValue());
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = oauth2Authorization.getToken(OAuth2RefreshToken.class);
        if (refreshToken != null) {
            authorization.setRefreshTokenValue(refreshToken.getToken().getTokenValue());
            authorization.setRefreshTokenExpiresAt(refreshToken.getToken().getExpiresAt());
            authorization.setRefreshTokenIssuedAt(refreshToken.getToken().getIssuedAt());
            authorization.setRefreshTokenMetadata(ConvertUtils.writeMap(refreshToken.getMetadata()));
        }

        OAuth2Authorization.Token<OidcIdToken> oidcIdToken = oauth2Authorization.getToken(OidcIdToken.class);
        if (oidcIdToken != null) {
            authorization.setOidcIdTokenValue(oidcIdToken.getToken().getTokenValue());
            authorization.setOidcIdTokenExpiresAt(oidcIdToken.getToken().getExpiresAt());
            authorization.setOidcIdTokenIssuedAt(oidcIdToken.getToken().getIssuedAt());
            authorization.setOidcIdTokenMetadata(ConvertUtils.writeMap(oidcIdToken.getMetadata()));
        }

        return authorization;
    }

    private OAuth2Authorization authorization2OAuth2Authorization(Authorization authorization) {
        if (authorization == null) return null;

        RegisteredClient registeredClient = customRegisteredClientRepository.findById(authorization.getRegisteredClientId());

        if (registeredClient == null) {
            throw new RuntimeException("The RegisteredClient was not exited");
        }

        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(authorization.getId())
                .principalName(authorization.getPrincipalName())
                .authorizationGrantType(ConvertUtils.string2AuthorizationGrantType(authorization.getAuthorizationGrantType()))
                .attributes(attributes->attributes.putAll(ConvertUtils.readMap(authorization.getAttributes())));

        if (authorization.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, authorization.getState());
        }

        if (authorization.getAuthorizationCodeValue() != null) {
            OAuth2AuthorizationCode oauth2AuthorizationCode = new OAuth2AuthorizationCode(authorization.getAuthorizationCodeValue(),
                    authorization.getAuthorizationCodeIssuedAt(),
                    authorization.getAuthorizationCodeExpiresAt());

            builder.token(oauth2AuthorizationCode, metadata -> metadata.putAll(ConvertUtils.readMap(authorization.getAuthorizationCodeMetadata())));
        }

        if (authorization.getAccessTokenValue() != null) {
            Assert.isTrue(OAuth2AccessToken.TokenType.BEARER.getValue().equals(authorization.getAccessTokenType()),
                    "access token type unsupported");

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                    authorization.getAccessTokenValue(), authorization.getAccessTokenIssuedAt(),
                    authorization.getAccessTokenExpiresAt());

            builder.token(accessToken, metadata -> metadata.putAll(ConvertUtils.readMap(authorization.getAccessTokenMetadata())));
        }

        if (authorization.getRefreshTokenValue() != null) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(authorization.getRefreshTokenValue(),
                    authorization.getRefreshTokenIssuedAt(), authorization.getRefreshTokenExpiresAt());
            builder.token(refreshToken, metadata -> metadata.putAll(ConvertUtils.readMap(authorization.getRefreshTokenMetadata())));
        }

        if (authorization.getOidcIdTokenValue() != null) {
            OidcIdToken oidcIdToken = new OidcIdToken(authorization.getOidcIdTokenValue(),
                    authorization.getOidcIdTokenIssuedAt(), authorization.getOidcIdTokenExpiresAt(),
                    ConvertUtils.readMap(authorization.getOidcIdTokenClaims()));
            builder.token(oidcIdToken, metadata -> metadata.putAll(ConvertUtils.readMap(authorization.getOidcIdTokenMetadata())));
        }

        return builder.build();
    }

}