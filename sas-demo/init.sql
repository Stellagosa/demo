CREATE DATABASE IF NOT EXISTS `db-oauth2-authorization-server` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

use `db-oauth2-authorization-server`;

CREATE TABLE oauth2_registered_client
(
    id                            varchar(100)                            NOT NULL,
    client_id                     varchar(100)                            NOT NULL,
    client_id_issued_at           timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp     DEFAULT NULL,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO oauth2_registered_client (id, client_id, client_id_issued_at, client_secret, client_secret_expires_at,
                                      client_name, client_authentication_methods, authorization_grant_types,
                                      redirect_uris, scopes, client_settings, token_settings)
VALUES ('34193645-e598-44e0-bbcc-8638fa870ae7', 'client-demo', '2022-06-24 16:15:37',
        '$2a$10$F74AJhQfR9FId9jDfJQ39eNFlckaI6h4U6OcvbSdopnq4BGDLHmFS', null, '34193645-e598-44e0-bbcc-8638fa870ae7',
        'client_secret_basic', 'refresh_token,client_credentials,authorization_code', 'https://www.baidu.com/',
        'read,openid,write',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.core.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

CREATE TABLE oauth2_authorization
(
    id                            varchar(100) NOT NULL,
    registered_client_id          varchar(100) NOT NULL,
    principal_name                varchar(200) NOT NULL,
    authorization_grant_type      varchar(100) NOT NULL,
    attributes                    blob          DEFAULT NULL,
    state                         varchar(500)  DEFAULT NULL,
    authorization_code_value      blob          DEFAULT NULL,
    authorization_code_issued_at  timestamp     DEFAULT NULL,
    authorization_code_expires_at timestamp     DEFAULT NULL,
    authorization_code_metadata   blob          DEFAULT NULL,
    access_token_value            blob          DEFAULT NULL,
    access_token_issued_at        timestamp     DEFAULT NULL,
    access_token_expires_at       timestamp     DEFAULT NULL,
    access_token_metadata         blob          DEFAULT NULL,
    access_token_type             varchar(100)  DEFAULT NULL,
    access_token_scopes           varchar(1000) DEFAULT NULL,
    oidc_id_token_value           blob          DEFAULT NULL,
    oidc_id_token_issued_at       timestamp     DEFAULT NULL,
    oidc_id_token_expires_at      timestamp     DEFAULT NULL,
    oidc_id_token_metadata        blob          DEFAULT NULL,
    refresh_token_value           blob          DEFAULT NULL,
    refresh_token_issued_at       timestamp     DEFAULT NULL,
    refresh_token_expires_at      timestamp     DEFAULT NULL,
    refresh_token_metadata        blob          DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE oauth2_authorization_consent
(
    registered_client_id varchar(100)  NOT NULL,
    principal_name       varchar(200)  NOT NULL,
    authorities          varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`                      bigint(20)   NOT NULL Auto_Increment COMMENT '用户id',
    `username`                varchar(100) NOT NULL COMMENT '用户名',
    `password`                varchar(100) NOT NULL COMMENT '用户密码密文',
    `nickname`                varchar(200)          DEFAULT NULL COMMENT '昵称',
    `mobile`                  varchar(20)           DEFAULT NULL COMMENT '用户手机号码',
    `description`             varchar(500)          DEFAULT NULL COMMENT '简介',
    `deleted`                 tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否已删除 0：已删除，1：未删除',
    `enabled`                 tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否有效用户',
    `account_non_expired`     tinyint(1)   NOT NULL DEFAULT 1 COMMENT '账号是否未过期',
    `credentials_non_expired` tinyint(1)   NOT NULL DEFAULT 1 COMMENT '密码是否未过期',
    `account_non_locked`      tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否未锁定',
    `created_time`            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time`            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_users_username` (`username`),
    UNIQUE KEY `ux_users_mobile` (`mobile`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户表';


INSERT INTO user (id, username, password, nickname, mobile, description, deleted,
                  enabled, account_non_expired, credentials_non_expired,
                  account_non_locked, created_time, updated_time)
VALUES (1, '张三', '$2a$10$nde/GL0Gcqp3fUYaYdYFC.uAGvsEtCK5C3SFGrgvN64dQxuWMROly', '尼古拉斯·张', '12329184721', null, 1, 1, 1,
        1, 1, '2022-06-24 16:16:52', '2022-06-24 16:16:52');



CREATE TABLE IF NOT EXISTS `role`
(
    `id`           bigint(20)   NOT NULL Auto_Increment COMMENT '角色id',
    `code`         varchar(100) NOT NULL COMMENT '角色code',
    `name`         varchar(200)          DEFAULT NULL COMMENT '角色名称',
    `deleted`      tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否已删除 0：已删除，1：未删除',
    `description`  varchar(500)          DEFAULT NULL COMMENT '简介',
    `created_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `rx_roles_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='角色表';


INSERT INTO role (id, code, name, deleted, description, created_time, updated_time)
VALUES (1, 'USER', '普通用户', 1, null, '2022-06-24 16:24:54', '2022-06-24 16:24:54');


CREATE TABLE IF NOT EXISTS `user_role_relation`
(
    `id`           bigint(20) NOT NULL Auto_Increment COMMENT '关系id',
    `user_id`      bigint(20) NOT NULL COMMENT '用户id',
    `role_id`      bigint(20) NOT NULL COMMENT '角色id',
    `deleted`      tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否已删除 0：已删除，1：未删除',
    `created_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_time` datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户和角色关系表';


INSERT INTO user_role_relation (id, user_id, role_id, deleted, created_time, updated_time)
VALUES (1, 1, 1, 1, '2022-06-24 16:25:12', '2022-06-24 16:25:12');

CREATE TABLE client
(
    id                            varchar(255)                           NOT NULL,
    client_id                     varchar(255)                           NOT NULL,
    client_id_issued_at           timestamp    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(255) DEFAULT NULL,
    client_secret_expires_at      timestamp    DEFAULT NULL,
    client_name                   varchar(255)                           NOT NULL,
    client_authentication_methods blob                                   NOT NULL,
    authorization_grant_types     blob                                   NOT NULL,
    redirect_uris                 blob         DEFAULT NULL,
    scopes                        blob                                   NOT NULL,
    client_settings               blob                                   NOT NULL,
    token_settings                blob                                   NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY `uk_client_client_id` (`client_id`)
);

CREATE TABLE authorization
(
    id                            varchar(255) NOT NULL,
    registered_client_id          varchar(255) NOT NULL,
    principal_name                varchar(255) NOT NULL,
    authorization_grant_type      varchar(255) NOT NULL,
    attributes                    blob         DEFAULT NULL,
    state                         varchar(500) DEFAULT NULL,
    authorization_code_value      blob         DEFAULT NULL,
    authorization_code_issued_at  timestamp    DEFAULT NULL,
    authorization_code_expires_at timestamp    DEFAULT NULL,
    authorization_code_metadata   blob         DEFAULT NULL,
    access_token_value            blob         DEFAULT NULL,
    access_token_issued_at        timestamp    DEFAULT NULL,
    access_token_expires_at       timestamp    DEFAULT NULL,
    access_token_metadata         blob         DEFAULT NULL,
    access_token_type             varchar(255) DEFAULT NULL,
    access_token_scopes           blob         DEFAULT NULL,
    refresh_token_value           blob         DEFAULT NULL,
    refresh_token_issued_at       timestamp    DEFAULT NULL,
    refresh_token_expires_at      timestamp    DEFAULT NULL,
    refresh_token_metadata        blob         DEFAULT NULL,
    oidc_id_token_value           blob         DEFAULT NULL,
    oidc_id_token_issued_at       timestamp    DEFAULT NULL,
    oidc_id_token_expires_at      timestamp    DEFAULT NULL,
    oidc_id_token_metadata        blob         DEFAULT NULL,
    oidc_id_token_claims          blob         DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE authorization_consent
(
    registered_client_id varchar(255)  NOT NULL,
    principal_name       varchar(255)  NOT NULL,
    authorities          varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);


