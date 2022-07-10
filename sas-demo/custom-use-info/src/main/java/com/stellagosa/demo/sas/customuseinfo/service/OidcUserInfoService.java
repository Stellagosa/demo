package com.stellagosa.demo.sas.customuseinfo.service;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Stellagosa
 * @Date: 2022/6/25 21:39
 * @Description:
 */
@Service
public class OidcUserInfoService {

    private final UserInfoRepository userInfoRepository = new UserInfoRepository();

    public OidcUserInfo loadUser(String username) {
        return new OidcUserInfo(this.userInfoRepository.findByUsername(username));
    }

    static class UserInfoRepository {

        private final Map<String, Map<String, Object>> userInfo = new HashMap<>();

        public UserInfoRepository() {
            this.userInfo.put("张三", createUser("张三"));
        }

        public Map<String, Object> findByUsername(String username) {
            return this.userInfo.get(username);
        }

        private static Map<String, Object> createUser(String username) {
            return OidcUserInfo.builder()
                    .subject(username)
                    .name("First Last")
                    .givenName("First")
                    .familyName("Last")
                    .middleName("Middle")
                    .nickname("User")
                    .preferredUsername(username)
                    .profile("https://example.com/" + username)
                    .picture("https://example.com/" + username + ".jpg")
                    .website("https://example.com")
                    .email(username + "@example.com")
                    .emailVerified(true)
                    .gender("female")
                    .birthdate("1970-01-01")
                    .zoneinfo("Europe/Paris")
                    .locale("en-US")
                    .phoneNumber("+1 (604) 555-1234;ext=5678")
                    .phoneNumberVerified("false")
                    .claim("address", Collections.singletonMap("formatted", "Champ de Mars\n5 Av. Anatole France\n75007 Paris\nFrance"))
                    .updatedAt("1970-01-01T00:00:00Z")
                    .build()
                    .getClaims();
        }
    }
}
