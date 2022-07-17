package com.stellagosa.demo.sas.password.config;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.Version;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @Author: Stellagosa
 * @Date: 2022/7/17 9:56
 * @Description:
 */
public final class OAuth2UsernamePasswordRequestAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = Version.SERIAL_VERSION_UID;

    private String authorizationUri;
    private Authentication clientPrincipal;
    private Authentication principal;
    private Set<String> scopes;
    private Map<String, Object> additionalParameters;

    private OAuth2UsernamePasswordRequestAuthenticationToken() {
        super(Collections.emptyList());
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    public String getAuthorizationUri() {
        return this.authorizationUri;
    }

    public Authentication getClientPrincipal() {
        return this.clientPrincipal;
    }

    public Set<String> getScopes() {
        return this.scopes;
    }

    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }

    public static OAuth2UsernamePasswordRequestAuthenticationToken.Builder with(@NonNull Authentication clientPrincipal, @NonNull Authentication principal) {
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        return new OAuth2UsernamePasswordRequestAuthenticationToken.Builder(clientPrincipal, principal);
    }

    public static final class Builder implements Serializable {
        private static final long serialVersionUID = Version.SERIAL_VERSION_UID;
        private String authorizationUri;
        private Authentication clientPrincipal;
        private Authentication principal;
        private Set<String> scopes;
        private Map<String, Object> additionalParameters;

        private Builder(Authentication clientPrincipal, Authentication principal) {
            this.clientPrincipal = clientPrincipal;
            this.principal = principal;
        }

        public OAuth2UsernamePasswordRequestAuthenticationToken.Builder authorizationUri(String authorizationUri) {
            this.authorizationUri = authorizationUri;
            return this;
        }

        public OAuth2UsernamePasswordRequestAuthenticationToken.Builder scopes(Set<String> scopes) {
            if (scopes != null) {
                this.scopes = new HashSet<>(scopes);
            }
            return this;
        }

        public OAuth2UsernamePasswordRequestAuthenticationToken.Builder additionalParameters(Map<String, Object> additionalParameters) {
            if (additionalParameters != null) {
                this.additionalParameters = new HashMap<>(additionalParameters);
            }
            return this;
        }

        public OAuth2UsernamePasswordRequestAuthenticationToken build() {
            Assert.hasText(this.authorizationUri, "authorizationUri cannot be empty");

            OAuth2UsernamePasswordRequestAuthenticationToken authentication =
                    new OAuth2UsernamePasswordRequestAuthenticationToken();

            authentication.authorizationUri = this.authorizationUri;
            authentication.clientPrincipal = this.clientPrincipal;
            authentication.principal = this.principal;
            authentication.scopes = Collections.unmodifiableSet(
                    !CollectionUtils.isEmpty(this.scopes) ?
                            this.scopes :
                            Collections.emptySet());
            authentication.additionalParameters = Collections.unmodifiableMap(
                    !CollectionUtils.isEmpty(this.additionalParameters) ?
                            this.additionalParameters :
                            Collections.emptyMap());
            return authentication;
        }
    }
}
