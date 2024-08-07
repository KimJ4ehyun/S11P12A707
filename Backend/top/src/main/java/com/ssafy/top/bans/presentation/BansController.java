package com.ssafy.top.bans.presentation;

import com.ssafy.top.bans.application.BansService;
import com.ssafy.top.bans.dto.request.BanAddRequest;
import com.ssafy.top.global.domain.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/focus-time")
public class BansController {
    private final BansService bansService;

    @PostMapping("/ban")
    public ResponseEntity<?> addBan(@RequestBody @Valid BanAddRequest banAddRequest) {
        Long userId = 1L;

        CommonResponseDto<List<String>> response = bansService.addBan(userId, banAddRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ban")
    public ResponseEntity<?> findBanList(){
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.findBanListByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/ban")
    public ResponseEntity<?> updateIsBan(@RequestBody @Valid BanAddRequest banDeleteRequest){
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.updateIsBan(userId, banDeleteRequest);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ban")
    public ResponseEntity<?> deleteBan(@RequestBody @Valid BanAddRequest banDeleteRequest){
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.deleteBan(userId, banDeleteRequest);

        return ResponseEntity.ok(response);
    }
}
