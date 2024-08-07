package com.ssafy.top.friends.dto.response;

import com.ssafy.top.users.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "친구 목록 조회 응답 DTO")
@Data
@Builder
public class FriendsResponse {
    @Schema(description = "친구 아이디")
    private Long userId;

    @Schema(description = "친구 닉네임")
    private String nickname;

    @Schema(description = "친구 화면 공유 여부")
    private Boolean screenShare;

    public static FriendsResponse toDto(Users user) {
        return FriendsResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .screenShare(user.getIsShare())
                .build();
    }
}
