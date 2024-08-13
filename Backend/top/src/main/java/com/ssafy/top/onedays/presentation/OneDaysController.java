package com.ssafy.top.onedays.presentation;

import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.onedays.dto.request.TimeGoalRequest;
import com.ssafy.top.onedays.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "일자별 집중시간 API", description = "날짜에 관련된 집중시간과 목표 집중시간 API")
@RestController
@RequiredArgsConstructor
public class OneDaysController {

    private final OneDaysService oneDaysService;

    @Operation(summary = "목표 집중시간 조회",
            description = "period 파라미터에 따라 일간/주간/월간 목표 시간을 조회한다.")
    @Parameters(value = {
            @Parameter(name = "period", description = "일간=day, 주간=week, 월간=month")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "목표 집중 시간 조회 성공",
                    content = @Content(schema = @Schema(implementation = TimeGoalResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "캘린더 데이터 조회 실패(부적절한 period 값)")
    })
    @GetMapping("/focus-time/goal")
    public ResponseEntity<?> findByTimeGoal(@RequestParam(name="period") String period, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        CommonResponseDto<?> response = oneDaysService.findTimeGoalByEmailAndPeriod(sessionUser.getEmail(), period);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "목표 집중 시간 수정",
            description = "오늘의 목표 집중 시간을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "목표 시간 추가 성공",
                    content = @Content(schema = @Schema(implementation = TimeGoalResponse.class)))
    })
    @PutMapping("/focus-time/goal")
    public ResponseEntity<?> updateTimeGoal(@RequestBody TimeGoalRequest timeGoal, HttpSession session) {

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = oneDaysService.updateTimeGoal(sessionUser.getEmail(), timeGoal);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
