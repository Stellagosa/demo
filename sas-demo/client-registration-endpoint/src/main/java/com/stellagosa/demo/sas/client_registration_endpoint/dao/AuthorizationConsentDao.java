package com.stellagosa.demo.sas.client_registration_endpoint.dao;

import com.stellagosa.demo.sas.client_registration_endpoint.entity.AuthorizationConsent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 11:33
 * @Description:
 */
@Mapper
public interface AuthorizationConsentDao {
    void save(AuthorizationConsent oauth2AuthorizationConsent2AuthorizationConsent);

    void update(AuthorizationConsent oauth2AuthorizationConsent2AuthorizationConsent);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    AuthorizationConsent findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
