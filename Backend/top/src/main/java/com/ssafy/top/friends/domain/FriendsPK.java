package com.ssafy.top.friends.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendsPK implements Serializable {
    private Long userId;
    private Long friendId;
}
