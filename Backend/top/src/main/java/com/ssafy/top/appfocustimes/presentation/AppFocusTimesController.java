package com.ssafy.top.appfocustimes.presentation;

import com.ssafy.top.appfocustimes.application.AppFocusTimesService;
import com.ssafy.top.appfocustimes.dto.request.AppNameAndTimeRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "App 관련 집중시간 API", description = "유저가 사용하는 App과 관련된 API 입니다.")
@RequiredArgsConstructor
@RestController
public class AppFocusTimesController {

    private final AppFocusTimesService appFocusTimesService;

    @Operation(summary = "일일 APP 집중시간 통계 조회",
            description = "일일 사용한 APP을 집중시간을 기준으로 내림차순 정렬하여 전달")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "프로그램 별 통계 조회 성공",
                    content = @Content(schema = @Schema(implementation = AppListResponse.class)))
    })
    @GetMapping("/dash/stats/app")
    public ResponseEntity<?> findAppFocusTimeList(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        CommonResponseDto<?> response = appFocusTimesService.findAppFocusTimeListByEmail(sessionUser.getEmail());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "스톱워치 APP 집중 시간 저장", description = "스톱 워치를 사용해 APP 집중 시간 저장")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "집중 시간 저장 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "집중 시간 저장 실패")
    })
    @PostMapping("/focus-time/app/custom")
    public ResponseEntity<?> saveCustomApp(@RequestBody AppNameAndTimeRequest appNameAndTimeRequest, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.saveCustomApp(sessionUser.getEmail(), appNameAndTimeRequest);

        if (response.getStatusCode() == 201) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }

    @Operation(summary = "APP 집중 시간 저장", description = "APP에 집중을 하고 있다 창이 바뀌면 집중 시간을 저장 또는 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "집중 시간 저장 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "집중 시간 저장 실패"),
    })
    @PutMapping("/focus-time/app")
    public ResponseEntity<?> save(@RequestBody AppNameRequest appNameRequest, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        CommonResponseDto<?> response = appFocusTimesService.save(sessionUser.getEmail(), appNameRequest);

        if (response.getStatusCode() == 201) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }
}
