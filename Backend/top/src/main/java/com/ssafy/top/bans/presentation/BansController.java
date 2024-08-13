package com.ssafy.top.bans.presentation;

import com.ssafy.top.bans.application.BansService;
import com.ssafy.top.bans.dto.request.BanRequest;
import com.ssafy.top.bans.dto.response.AppNameAndTimeResponse;
import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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
            @ApiResponse(responseCode = "409",
                    description = "시간 측정 금지 목록 추가 실패(이미 등록된 URL 또는 프로그램)")
    })
    @PostMapping("/ban")
    public ResponseEntity<?> addBan(HttpSession session, @RequestBody @Valid BanRequest banRequest) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = bansService.addBan(sessionUser.getEmail(), banRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시간 측정 금지 목록 조회",
            description = "시간 측정 금지 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "시간 측정 금지 목록 조회",
                    content = @Content(schema = @Schema(implementation = AppNameAndTimeResponse.class)))
    })
    @GetMapping("/ban")
    public ResponseEntity<?> findBanList(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = bansService.findBanListByUserId(sessionUser.getEmail());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시간 측정 금지 목록에서 후보 목록으로 변경",
            description = "시간 측정 금지 목록에 있는 URL 또는 프로그램을 후보 목록으로 변경한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "시간 측정 금지 목록에서 후보 목록으로 변경 성공",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404",
                    description = "시간 측정 금지 목록에서 후보 목록으로 변경 실패(존재하지 않는 URL 또는 프로그램)")
    })
    @PutMapping("/ban")
    public ResponseEntity<?> updateIsBan(HttpSession session, @RequestBody @Valid BanRequest banDeleteRequest){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = bansService.updateIsBan(sessionUser.getEmail(), banDeleteRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시간 측정 금지 목록 삭제",
            description = "시간 측정 금지 목록에 있는 URL 또는 프로그램을 삭제한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "시간 측정 금지 목록 삭제 성공",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404",
                    description = "시간 측정 금지 목록 삭제 실패(존재하지 않는 URL 또는 프로그램)")
    })
    @DeleteMapping("/ban")
    public ResponseEntity<?> deleteBan(HttpSession session, @RequestBody @Valid BanRequest banDeleteRequest){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = bansService.deleteBan(sessionUser.getEmail(), banDeleteRequest);

        return ResponseEntity.ok(response);
    }
}
