package com.ssafy.top.friends.dto.response;

import com.ssafy.top.users.domain.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendsResponse {
    private Long userId;

    private String nickname;

    private Boolean screenShare;

    public static FriendsResponse toDto(Users user) {
        return FriendsResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .screenShare(user.getIsShare())
                .build();
    }
}
