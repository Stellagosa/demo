package com.stellagosa.demo.sas.client_registration_endpoint.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.stellagosa.demo.sas.client_registration_endpoint.dao.ClientDao;
import com.stellagosa.demo.sas.client_registration_endpoint.entity.Client;
import com.stellagosa.demo.sas.client_registration_endpoint.utils.ConvertUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/5 11:44
 * @Description:
 */
@Component
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientDao clientDao;
    private final PasswordEncoder passwordEncoder;

    public CustomRegisteredClientRepository(ClientDao clientDao, PasswordEncoder passwordEncoder) {
        Assert.notNull(clientDao, "ClientDao cannot be null");
        Assert.notNull(passwordEncoder, "PasswordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
        this.clientDao = clientDao;
    }

    @Transactional
    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "RegisteredClient cannot be null");
        Client existing = clientDao.findById(registeredClient.getId());
        if (existing == null) {
            clientDao.save(registeredClient2Client(registeredClient));
        } else {
            clientDao.update(registeredClient2Client(registeredClient));
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        return client2RegisteredClient(clientDao.findById(id));
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return client2RegisteredClient(clientDao.findByClientId(clientId));
    }

    private RegisteredClient client2RegisteredClient(Client client) {
        if (client == null) return null;
        return RegisteredClient.withId(client.getId())
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethods(methods -> methods.addAll(
                        StringUtils.commaDelimitedListToSet(client.getClientAuthenticationMethods())
                                .stream()
                                .map(ConvertUtils::string2ClientAuthenticationMethod)
                                .collect(Collectors.toSet())
                ))
                .authorizationGrantTypes(types -> types.addAll(
                        StringUtils.commaDelimitedListToSet(client.getAuthorizationGrantTypes())
                                .stream()
                                .map(ConvertUtils::string2AuthorizationGrantType)
                                .collect(Collectors.toSet())
                ))
                .clientIdIssuedAt(client.getClientIdIssuedAt())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt())
                .redirectUris(redirectUrls -> redirectUrls.addAll(StringUtils.commaDelimitedListToSet(client.getRedirectUris())))
                .scopes(scopes -> scopes.addAll(StringUtils.commaDelimitedListToSet(client.getScopes())))
                .clientSettings(ClientSettings.withSettings(
                                ConvertUtils.string2Object(client.getClientSettings(), new TypeReference<>() {}))
                                .build()
                )
                .tokenSettings(TokenSettings.withSettings(
                        ConvertUtils.string2Object(client.getTokenSettings(), new TypeReference<>() {}))
                        .build()
                )
                .build();
    }

    private Client registeredClient2Client(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "RegisteredClient cannot be null");

        Client client = new Client();
        client.setId(registeredClient.getId());
        client.setClientId(registeredClient.getClientId());
        client.setClientSecret(passwordEncoder.encode(registeredClient.getClientSecret()));

        client.setClientName(registeredClient.getClientName());
        client.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt());
        client.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt());
        client.setRedirectUris(StringUtils.collectionToDelimitedString(registeredClient.getRedirectUris(), ","));

        client.setScopes(StringUtils.collectionToDelimitedString(registeredClient.getScopes(), ","));

        client.setAuthorizationGrantTypes(StringUtils.collectionToDelimitedString(
                registeredClient.getAuthorizationGrantTypes()
                        .stream()
                        .map(AuthorizationGrantType::getValue)
                        .collect(Collectors.toSet()), ","));

        client.setClientAuthenticationMethods(StringUtils.collectionToDelimitedString(
                registeredClient.getClientAuthenticationMethods()
                        .stream()
                        .map(ClientAuthenticationMethod::getValue)
                        .collect(Collectors.toSet()), ",")
        );
        client.setClientSettings(ConvertUtils.object2String(registeredClient.getClientSettings().getSettings()));
        client.setTokenSettings(ConvertUtils.object2String(registeredClient.getTokenSettings().getSettings()));

        return client;
    }
}
