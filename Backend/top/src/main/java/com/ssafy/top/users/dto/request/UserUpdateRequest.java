package com.ssafy.top.users.dto.request;

import com.ssafy.top.global.exception.validation.AllowedBoolean;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank
    private String nickname;

    @AllowedBoolean
    private Boolean isShare;

    @AllowedBoolean
    private Boolean isActive;
}
