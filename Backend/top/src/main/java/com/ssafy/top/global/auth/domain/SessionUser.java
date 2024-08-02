package com.ssafy.top.global.auth.domain;

import com.ssafy.top.users.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SessionUser implements Serializable {

    private String loginId;

    public SessionUser(Users user) {
        this.loginId = user.getEmail();

    }
}
