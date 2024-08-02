package com.ssafy.top.global.auth.application;

import com.ssafy.top.global.auth.domain.OauthAttributes;
import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final static String ROLE = "USER";

    private final UsersRepository usersRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //카카오, 구글, 네이버 등 로그인 서비스를 구분
        //구글만 필요하므로 생략
        //String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OauthAttributes attributes = OauthAttributes.of(userNameAttributeName, oAuth2User.getAttributes());
        Users user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(ROLE)),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Users saveOrUpdate(OauthAttributes attributes) {
        Users user = usersRepository.findByEmail(attributes.getLoginId())
                .orElse(usersRepository.save(attributes.toEntity()));
        log.info("로그인 한 유저: {}",user.getEmail());

        return user;

    }
}
