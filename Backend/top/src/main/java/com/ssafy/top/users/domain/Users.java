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

    private String loginId;

    private Boolean screenShare;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateScreenShare(Boolean screenShare) {
        this.screenShare = screenShare;
    }
}
