package com.ssafy.top.bans.presentation;

import com.ssafy.top.bans.application.BansService;
import com.ssafy.top.bans.dto.request.BanAddRequest;
import com.ssafy.top.global.domain.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/focus-time")
public class BansController {
    private final BansService bansService;

    @PostMapping("/ban")
    public ResponseEntity<?> addBan(@RequestBody BanAddRequest banAddRequest) {
        Long userId = 1L;

        CommonResponseDto<List<String>> response = bansService.addBan(userId, banAddRequest);

        return ResponseEntity.ok(response);
    }
}
