package com.stellagosa.demo.sas.client_registration_endpoint.dao;

import com.stellagosa.demo.sas.client_registration_endpoint.entity.Client;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Stellagosa
 * @Date: 2022/6/30 15:40
 * @Description:
 */
@Mapper
public interface ClientDao {
    Client findByClientId(String clientId);

    Client findById(String id);

    void save(Client client);

    void update(Client registeredClient2Client);
}
