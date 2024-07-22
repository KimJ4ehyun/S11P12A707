package com.ssafy.top.bans.domain;

import com.ssafy.top.users.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Bans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
