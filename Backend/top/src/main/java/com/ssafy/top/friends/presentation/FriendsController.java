package com.ssafy.top.friends.presentation;

import com.ssafy.top.friends.application.FriendsService;
import com.ssafy.top.friends.dto.response.FriendsResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping
    public ResponseEntity<?> findFriends() {
        Long userId = 1L;

        CommonResponseDto<List<FriendsResponse>> response = friendsService.getFriends(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("request/{friendId}")
    public ResponseEntity<?> requestFriends(
            @PathVariable Long friendId) {
        Long userId = 1L;

        CommonResponseDto<?> response = friendsService.requestFriends(userId, friendId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("response/{friendId}")
    public ResponseEntity<?> responseFriends(
            @PathVariable Long friendId) {
        Long userId = 1L;

        CommonResponseDto<?> response = friendsService.responseFriends(userId, friendId);

        return ResponseEntity.ok(response);
    }
}
