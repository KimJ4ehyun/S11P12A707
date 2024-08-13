package com.ssafy.top.bans.domain;

import com.ssafy.top.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Bans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id")
    private Long id;

    private String name;

    private boolean isBan;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public void updateIsBan(boolean isBan) {
        this.isBan = isBan;
    }
}
