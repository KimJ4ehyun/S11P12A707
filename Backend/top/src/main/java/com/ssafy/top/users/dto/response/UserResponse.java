package com.ssafy.top.users.dto.response;

import com.ssafy.top.users.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "내 정보 조회 응답 DTO")
@Data
@Builder
public class UserResponse {

    @Schema(description = "나의 닉네임")
    private String nickname;

    @Schema(description = "로그인 아이디")
    private String email;

    @Schema(description = "화면 공유 여부")
    private Boolean screenShare;

    public static UserResponse toDto(Users user) {
        return UserResponse.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .screenShare(user.getIsShare())
                .build();
    }
}
