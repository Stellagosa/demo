package com.stellagosa.demo.sas.client_registration_endpoint.entity;

import lombok.Data;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 11:37
 * @Description:
 */
@Data
public class AuthorizationConsent {

    private String registeredClientId;
    private String principalName;
    private String authorities;

}
