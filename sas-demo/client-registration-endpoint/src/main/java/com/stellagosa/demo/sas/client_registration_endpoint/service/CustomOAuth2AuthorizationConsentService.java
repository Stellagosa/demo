package com.stellagosa.demo.sas.client_registration_endpoint.service;

import com.stellagosa.demo.sas.client_registration_endpoint.dao.AuthorizationConsentDao;
import com.stellagosa.demo.sas.client_registration_endpoint.entity.AuthorizationConsent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 8:03
 * @Description:
 */
@Component
public class CustomOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final AuthorizationConsentDao authorizationConsentDao;

    public CustomOAuth2AuthorizationConsentService(AuthorizationConsentDao authorizationConsentDao) {
        this.authorizationConsentDao = authorizationConsentDao;
    }

    @Transactional
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        Assert.notNull(authorizationConsent, "AuthorizationConsent cannot be null");

        AuthorizationConsent existing = authorizationConsentDao.findByRegisteredClientIdAndPrincipalName(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
        if (existing == null) {
            authorizationConsentDao.save(oauth2AuthorizationConsent2AuthorizationConsent(authorizationConsent));
        } else {
            authorizationConsentDao.update(oauth2AuthorizationConsent2AuthorizationConsent(authorizationConsent));
        }
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        authorizationConsentDao.deleteByRegisteredClientIdAndPrincipalName(authorizationConsent.getRegisteredClientId(),
                authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return authorizationConsent2OAuth2AuthorizationConsent(
                authorizationConsentDao
                        .findByRegisteredClientIdAndPrincipalName(registeredClientId, principalName));
    }


    private OAuth2AuthorizationConsent authorizationConsent2OAuth2AuthorizationConsent(AuthorizationConsent authorizationConsent) {
        if (authorizationConsent == null) return null;
        return OAuth2AuthorizationConsent.withId(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName())
                .authorities(authorities ->
                        authorities.addAll(
                                StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())
                                        .stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toSet()))).build();
    }

    private AuthorizationConsent oauth2AuthorizationConsent2AuthorizationConsent(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        AuthorizationConsent authorizationConsent = new AuthorizationConsent();
        authorizationConsent.setRegisteredClientId(oAuth2AuthorizationConsent.getRegisteredClientId());
        authorizationConsent.setPrincipalName(oAuth2AuthorizationConsent.getPrincipalName());
        authorizationConsent.setAuthorities(
                StringUtils.collectionToDelimitedString(
                        oAuth2AuthorizationConsent.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet()), ",")
        );
        return authorizationConsent;
    }

}
