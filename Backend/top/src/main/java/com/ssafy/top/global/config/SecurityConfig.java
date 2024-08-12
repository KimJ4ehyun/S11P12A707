package com.ssafy.top.global.config;

import com.ssafy.top.global.auth.application.CustomOAuth2SuccessHandler;
import com.ssafy.top.global.auth.application.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{

    private final CustomOauth2UserService customOauth2UserService;

    @Bean
    public AuthenticationSuccessHandler customOAuth2SuccessHandler() {
        return new CustomOAuth2SuccessHandler();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                .authorizeHttpRequests((authorizeRequest) -> authorizeRequest.requestMatchers("/user/check").permitAll()
                        .anyRequest().authenticated())

                .oauth2Login(oauth ->{
                    oauth.userInfoEndpoint(c -> c.userService(customOauth2UserService))
                            .successHandler(customOAuth2SuccessHandler())
                            .permitAll();
                });

        return http.build();
    }
}
