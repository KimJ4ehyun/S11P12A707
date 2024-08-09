package com.ssafy.top.hourfocustimes.presentation;

import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.hourfocustimes.application.HourFocusTimesService;
import com.ssafy.top.hourfocustimes.dto.request.FocusTimeRequest;
import com.ssafy.top.hourfocustimes.dto.request.HourRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "단위 시간 기준 집중시간 관련 API", description = "하루 내 통계 제시를 위한 단위 시간 집중 시간 API 입니다.")
@RestController
@RequiredArgsConstructor
public class HourFocusTimesController {

    private final HourFocusTimesService hourFocusTimesService;
    @Operation(summary = "시간 단위 기준 시간 추가",
            description = "hour 단위로 집중 시간 데이터 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "단위 시간 집중 시간 추가 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "단위 시간 집중 시간 추가 실패"),
            @ApiResponse(responseCode = "409",
                    description = "이미 존재하는 단위 집중 시간")
    })
    @PostMapping("/focus-time")
    public ResponseEntity<?> save(@RequestBody HourRequest hourRequest, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hourFocusTimesService.save(sessionUser.getEmail(), hourRequest));
    }

    @Operation(summary = "시간 단위 기준 집중 시간 업데이트",
            description = "hour 단위로 집중 시간 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "단위 시간 집중 시간 업데이트 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "단위 시간 집중 시간 업데이트 실패")
    })
    @PutMapping("/focus-time")
    public ResponseEntity<?> updateFocusTime(@RequestBody FocusTimeRequest focusTimeRequest, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return ResponseEntity.ok().body(hourFocusTimesService.updateFocusTimePeriodically(sessionUser.getEmail(), focusTimeRequest));
    }
}
