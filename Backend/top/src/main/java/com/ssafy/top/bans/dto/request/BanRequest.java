package com.ssafy.top.bans.dto.request;

import com.ssafy.top.bans.domain.Bans;
import com.ssafy.top.users.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "시간 측정 금지 목록 DTO")
@Data
public class BanRequest {
    @Schema(description = "URL 또는 프로그램 명")
    @NotBlank
    private String name;

    public Bans toEntity(Users user) {
        return Bans.builder()
                .name(name)
                .user(user)
                .isBan(true)
                .build();
    }
}
