package com.ssafy.top.friends.presentation;

import com.ssafy.top.friends.application.FriendsService;
import com.ssafy.top.friends.dto.response.FriendsResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
