package com.ssafy.top.friends.domain;

import com.ssafy.top.users.domain.Users;
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
public class Friends {
    @EmbeddedId
    private FriendsPK friendsPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    private Users friends;

    @Enumerated(EnumType.STRING)
    Relation relation;
}
