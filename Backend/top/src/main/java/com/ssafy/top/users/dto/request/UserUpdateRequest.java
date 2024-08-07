package com.ssafy.top.users.dto.request;

import com.ssafy.top.global.exception.validation.AllowedBoolean;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "유저 정보 수정 요청 DTO")
@Data
public class UserUpdateRequest {

    @Schema(description = "수정할 닉네임")
    @NotBlank
    private String nickname;

    @Schema(description = "수정할 화면 공유 허용 여부")
    @AllowedBoolean
    private Boolean isShare;

    @Schema(description = "수정할 익스텐션 활성화 여부")
    @AllowedBoolean
    private Boolean isActive;
}
