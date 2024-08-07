package com.ssafy.top.users.dto.response;

import com.ssafy.top.users.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "유저 정보 수정 응답 DTO")
@Data
@Builder
public class UserUpdateResponse {

    @Schema(description = "수정된 닉네임")
    private String nickname;

    @Schema(description = "수정된 화면 공유 여부")
    private Boolean isShare;

    @Schema(description = "수정된 익스텐션 활성화 여부")
    private Boolean isActive;

    public static UserUpdateResponse toDto(Users user) {
        return UserUpdateResponse.builder()
                .nickname(user.getNickname())
                .isShare(user.getIsShare())
                .isActive(user.getIsActive())
                .build();
    }
}
