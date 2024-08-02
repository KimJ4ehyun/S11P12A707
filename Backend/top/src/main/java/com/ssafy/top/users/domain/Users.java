package com.ssafy.top.users.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;

    private String email;

    private Boolean isShare;

    private Boolean isActive;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateIsShare(Boolean isShare) {
        this.isShare = isShare;
    }

    public void updateIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
