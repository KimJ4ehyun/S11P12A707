package com.ssafy.top.users.presentation;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.users.application.UsersService;
import com.ssafy.top.users.dto.response.UserResponse;
import com.ssafy.top.users.dto.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<?> findUsersByNickname(@RequestParam String nickname) {
        Long userId = 1L;

        CommonResponseDto<List<UsersResponse>> response = usersService.getUsersByLoginId(userId, nickname);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<?> findUser() {
        Long userId = 1L;

        CommonResponseDto<UserResponse> response = usersService.getUser(userId);

        return ResponseEntity.ok(response);
    }
}
