package com.stellagosa.demo.sas.client_registration_endpoint.dao;

import com.stellagosa.demo.sas.client_registration_endpoint.entity.Authorization;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/6 10:09
 * @Description:
 */
@Mapper
public interface AuthorizationDao {

    void save(Authorization oauth2Authorization2Authorization);

    void deleteById(String id);

    Authorization findById(String id);

    Authorization findByStateOrCodeOrAccessTokenOrRefreshToken(String token);

    Authorization findByState(String token);

    Authorization findByAccessTokenValue(String token);

    Authorization findByAuthorizationCodeValue(String token);

    Authorization findByRefreshTokenValue(String token);

    void update(Authorization oauth2Authorization2Authorization);
}
