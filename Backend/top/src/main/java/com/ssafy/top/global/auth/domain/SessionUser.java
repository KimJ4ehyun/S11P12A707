package com.ssafy.top.global.auth.domain;

import com.ssafy.top.users.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class SessionUser implements Serializable {
    private String email;

    public SessionUser(Users user) {
        this.email = user.getEmail();
    }
}
