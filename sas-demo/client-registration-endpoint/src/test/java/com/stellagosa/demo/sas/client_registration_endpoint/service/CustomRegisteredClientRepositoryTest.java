package com.stellagosa.demo.sas.client_registration_endpoint.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.UUID;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/5 17:20
 * @Description:
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CustomRegisteredClientRepositoryTest {

    @Autowired
    CustomRegisteredClientRepository customRegisteredClientRepository;
    @Test
    void save() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-demo")
                .clientName("client-demo")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientSecret("secret")
                .redirectUri("https://www.baidu.com/")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("read").scope("write").scope(OidcScopes.OPENID)
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .requireProofKey(false)
                        .build()
                )
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(2))
                        .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
                        .refreshTokenTimeToLive(Duration.ofHours(4))
                        .reuseRefreshTokens(true)
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .build())
                .build();
        customRegisteredClientRepository.save(registeredClient);
    }

    @Test
    void findById() {
        String id = "ecd1e9a9-31ab-4df1-b879-c057ea878020";
        RegisteredClient registeredClient = customRegisteredClientRepository.findById(id);
        Assertions.assertNotNull(registeredClient);
        Assertions.assertNotNull(registeredClient.getClientSettings());
        Assertions.assertNotNull(registeredClient.getTokenSettings());
        Assertions.assertEquals(registeredClient.getId(), id);
    }

    @Test
    void findByClientId() {
        String clientId = "N-LXUWRH7hiu7m1JuRlLW_JIJEkeXW_v0ipagUPXlG4";
        RegisteredClient registeredClient = customRegisteredClientRepository.findByClientId(clientId);
        Assertions.assertNotNull(registeredClient);
        Assertions.assertNotNull(registeredClient.getClientSettings());
        Assertions.assertNotNull(registeredClient.getTokenSettings());
        Assertions.assertEquals(registeredClient.getClientId(), clientId);
    }
}