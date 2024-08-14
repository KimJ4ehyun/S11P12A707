package com.ssafy.top.global.auth.application;

import com.ssafy.top.bans.domain.Bans;
import com.ssafy.top.bans.domain.BansRepository;
import com.ssafy.top.global.auth.domain.OauthAttributes;
import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.users.domain.Users;
import com.ssafy.top.users.domain.UsersRepository;
import com.ssafy.top.widgets.domain.WidgetType;
import com.ssafy.top.widgets.domain.Widgets;
import com.ssafy.top.widgets.domain.WidgetsRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final static String ROLE = "USER";

    private final UsersRepository usersRepository;
    private final BansRepository bansRepository;
    private final WidgetsRepository widgetsRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

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
        Optional<Users> user = usersRepository.findByEmail(attributes.getEmail());

        if(user.isPresent()) {
            return user.get();
        }

        Users newUser = usersRepository.save(attributes.toEntity());

        // 초기 위젯
        initWidget(newUser);

        // 초기 URL 또는 프로그램
        initBan(newUser);

        return newUser;
    }

    private void initWidget(Users user) {
        List<Widgets> widgets = new ArrayList<>();

        Widgets w1 = Widgets.builder()
                .name(WidgetType.TodayFocusBigWithoutComparison)
                .x(0)
                .y(0)
                .width(3)
                .height(1)
                .user(user)
                .build();
        widgets.add(w1);

        Widgets w2 = Widgets.builder()
                .name(WidgetType.WeekFocusBigWithoutComparison)
                .x(3)
                .y(0)
                .width(3)
                .height(1)
                .user(user)
                .build();
        widgets.add(w2);

        Widgets w3 = Widgets.builder()
                .name(WidgetType.MonthFocusBigWithoutComparison)
                .x(6)
                .y(0)
                .width(3)
                .height(1)
                .user(user)
                .build();
        widgets.add(w3);

        Widgets w4 = Widgets.builder()
                .name(WidgetType.TotalFocusBig)
                .x(9)
                .y(0)
                .width(3)
                .height(1)
                .user(user)
                .build();
        widgets.add(w4);

        Widgets w5 = Widgets.builder()
                .name(WidgetType.TimeLine)
                .x(0)
                .y(1)
                .width(7)
                .height(4)
                .user(user)
                .build();
        widgets.add(w5);

        Widgets w6 = Widgets.builder()
                .name(WidgetType.BannedProgramList)
                .x(7)
                .y(1)
                .width(5)
                .height(4)
                .user(user)
                .build();
        widgets.add(w6);

        Widgets w7 = Widgets.builder()
                .name(WidgetType.FocusTimeEachProgramsPercentage)
                .x(0)
                .y(5)
                .width(7)
                .height(4)
                .user(user)
                .build();
        widgets.add(w7);

        Widgets w8 = Widgets.builder()
                .name(WidgetType.CalendarCheck)
                .x(7)
                .y(5)
                .width(5)
                .height(4)
                .user(user)
                .build();
        widgets.add(w8);

        widgetsRepository.saveAll(widgets);
    }

    private void initBan(Users user) {
        String[] names = {"i11a707.p.ssafy.io", "www.youtube.com", "comic.naver.com", "chatgpt.com", "www.coupang.com",
                "www.netflix.com", "www.tving.com", "kakaotalk.exe", "mattermost.exe"};

        List<Bans> bans = new ArrayList<>();

        for(String name : names) {
            bans.add(Bans.builder()
                    .name(name)
                    .isBan(true)
                    .user(user)
                    .build());
        }

        bansRepository.saveAll(bans);
    }
}
