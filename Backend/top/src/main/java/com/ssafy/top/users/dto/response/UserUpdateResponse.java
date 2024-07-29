package com.ssafy.top.users.dto.response;

import com.ssafy.top.users.domain.Users;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateResponse {
    private String nickname;

    private Boolean isShare;

    private Boolean isActive;

    public static UserUpdateResponse toDto(Users user) {
        return UserUpdateResponse.builder()
                .nickname(user.getNickname())
                .isShare(user.getIsShare())
                .isActive(user.getIsActive())
                .build();
    }
}
