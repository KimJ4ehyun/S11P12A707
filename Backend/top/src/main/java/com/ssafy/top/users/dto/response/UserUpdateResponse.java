package com.ssafy.top.users.dto.response;

import com.ssafy.top.users.domain.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateResponse {
    private String nickname;

    private Boolean screenShare;

    public static UserUpdateResponse toDto(Users user) {
        return UserUpdateResponse.builder()
                .nickname(user.getNickname())
                .screenShare(user.getScreenShare())
                .build();
    }
}
