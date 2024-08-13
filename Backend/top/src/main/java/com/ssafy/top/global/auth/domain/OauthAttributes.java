package com.ssafy.top.global.auth.domain;

import com.ssafy.top.users.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class OauthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static OauthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OauthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes) {
        return OauthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .nickname(email)
                .email(email)
                .isShare(false)
                .isActive(true)
                .build();
    }
}
