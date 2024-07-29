package com.ssafy.top.users.dto.response;

import com.ssafy.top.users.domain.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String nickname;

    private String loginId;

    private Boolean screenShare;

    public static UserResponse toDto(Users user) {
        return UserResponse.builder()
                .nickname(user.getNickname())
                .loginId(user.getLoginId())
                .screenShare(user.getIsShare())
                .build();
    }
}
