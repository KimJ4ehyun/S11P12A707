package com.ssafy.top.bans.dto.request;

import com.ssafy.top.bans.domain.Bans;
import com.ssafy.top.users.domain.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BanAddRequest {
    @NotBlank
    private String name;

    public Bans toEntity(Users user) {
        return Bans.builder()
                .name(name)
                .user(user)
                .build();
    }
}
