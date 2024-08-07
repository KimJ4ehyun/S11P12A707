package com.ssafy.top.bans.presentation;

import com.ssafy.top.bans.application.BansService;
import com.ssafy.top.bans.dto.request.BanRequest;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.users.dto.response.UsersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "시간 측정 금지 목록 API", description = "시간 측정 금지 목록에 관련된 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/focus-time")
public class BansController {
    private final BansService bansService;

    @Operation(summary = "시간 측정 금지 목록 추가",
            description = "URL 또는 프로그램 명으로 시간 측정 금지 목록을 추가한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "시간 측정 금지 목록 추가 성공"),
            @ApiResponse(responseCode = "400",
                    description = "시간 측정 금지 목록 추가 실패(올바르지 않는 도메인 형식)"),
            @ApiResponse(responseCode = "400",
                    description = "시간 측정 금지 목록 추가 실패(이미 등록된 URL 또는 프로그램)")
    })
    @PostMapping("/ban")
    public ResponseEntity<?> addBan(@RequestBody @Valid BanRequest banRequest) {
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.addBan(userId, banRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ban")
    public ResponseEntity<?> findBanList(){
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.findBanListByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/ban")
    public ResponseEntity<?> updateIsBan(@RequestBody @Valid BanRequest banDeleteRequest){
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.updateIsBan(userId, banDeleteRequest);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ban")
    public ResponseEntity<?> deleteBan(@RequestBody @Valid BanRequest banDeleteRequest){
        Long userId = 1L;

        CommonResponseDto<?> response = bansService.deleteBan(userId, banDeleteRequest);

        return ResponseEntity.ok(response);
    }
}
