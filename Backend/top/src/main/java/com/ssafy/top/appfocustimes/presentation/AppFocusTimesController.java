package com.ssafy.top.appfocustimes.presentation;

import com.ssafy.top.appfocustimes.application.AppFocusTimesService;
import com.ssafy.top.appfocustimes.dto.request.AppNameAndTimeRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNamePeriodRequest;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.*;
import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
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

@Tag(name = "App 관련 집중시간 API", description = "유저가 사용하는 App과 관련된 API 입니다.")
@RequiredArgsConstructor
@RestController
public class AppFocusTimesController {

    private final AppFocusTimesService appFocusTimesService;

    @Operation(summary = "일간/주간/월간 집중시간 통계 조회",
            description = "period 파라미터에 따라 일간/주간/월간 집중시간 통계를 조회한다.")
    @Parameters(value = {
            @Parameter(name = "period", description = "일간=day, 주간=week, 월간=month")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "통계 조회 성공",
                    content = @Content(schema = @Schema(implementation = PeriodTotalFocusTimeResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "통계 조회 실패(부적절한 period 값)")
    })
    @GetMapping("/dash/stats/focus-time")
    public ResponseEntity<?> findTotalFocusTimeByPeriod(@RequestParam(name = "period") String period, HttpSession session) {

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.findTotalFocusTimeByEmailAndPeriod(sessionUser.getEmail(), period);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "캘린더에 표시할 데이터 조회",
            description = "year, month, day 파라미터에 따라 해당 날짜 집중시간을 조회한다.")
    @Parameters(value = {
            @Parameter(name = "year", description = "year = 2024"),
            @Parameter(name = "month", description = "month = 8"),
            @Parameter(name = "day", description = "month = 8")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "캘린더 데이터 조회 성공",
                    content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400",
                    description = "캘린더 데이터 조회 실패(부적절한 year/month/day 값)")
    })
    @GetMapping("/dash/calendar")
    public ResponseEntity<?> findFocusTimeListByYearAndMonth(
            HttpSession session,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "day") int day
    ){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService
                .findFocusTimeListByEmailAndYearAndMonth(sessionUser.getEmail(), year, month, day);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "전체 집중 시간 조회",
            description = "처음 시작한 때부터 현재 시점까지의 총 집중 시간을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "전체 집중 시간 조회 성공",
                    content = @Content(schema = @Schema(implementation = TotalFocusTimeResponse.class)))
    })
    @GetMapping("/dash/stats/focus-time/total")
    public ResponseEntity<?> findWholeTotalFocusTime(HttpSession session) {

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.findWholeTotalFocusTimeByEmail(sessionUser.getEmail());

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "일간/주간/월간 통계 그래프에 사용되는 단위 집중 시간 조회",
            description = "period 파라미터에 따라 일간/주간/월간 집중시간 통계를 조회한다.")
    @Parameters(value = {
            @Parameter(name = "period", description = "일간=day, 주간=week, 월간=month")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "통계 조회 성공",
                    content = @Content(schema = @Schema(implementation = FocusTimeListDayResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "통계 조회 실패(부적절한 period 값)")
    })
    @GetMapping("/dash/stats/focus-time/detail")
    public ResponseEntity<CommonResponseDto<?>> findFocusTimeListByPeriod(@RequestParam(name = "period") String period, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.findFocusTimeListByEmailAndPeriod(sessionUser.getEmail(), period);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "스트릭에 표시할 데이터 조회",
            description = "month 파라미터에 따라 1개월/6개월 집중시간 통계를 조회한다.")
    @Parameters(value = {
            @Parameter(name = "month", description = "1개월 = 1, 6개월 = 6")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "스트릭 데이터 조회 성공",
                    content = @Content(schema = @Schema(implementation = FocusTimeListResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "스트릭 데이터 조회 실패(부적절한 month 값)")
    })
    @GetMapping("/dash/streak")
    public ResponseEntity<CommonResponseDto<?>> findFocusTimeListByMonth(@RequestParam(name="month") int month, HttpSession session){

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.findFocusTimeListByEmailAndMonth(sessionUser.getEmail(), month);

        return ResponseEntity.ok().body(response);
    }

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

    @Operation(summary = "전체 인원 대비 집중 시간 백분율 조회",
            description = "전체 인원 대비 일간/주간/월간 백분율을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "백분율 조회 성공",
                    content = @Content(schema = @Schema(implementation = FocusTimePercentResponse.class)))
    })
    @GetMapping("/dash/stats/focus-time/percent")
    public ResponseEntity<?> findFocusTimePercent(HttpSession session){

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.findFocusTimePercentByEmail(sessionUser.getEmail());

        return ResponseEntity.ok().body(response);
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
    public ResponseEntity<?> updateAppFocusTime(@RequestBody AppNameRequest appNameRequest, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        CommonResponseDto<?> response = appFocusTimesService.updateAppFocusTime(sessionUser.getEmail(), appNameRequest);

        if (response.getStatusCode() == 201) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
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

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/focus-time")
    public ResponseEntity<?> updateFocusTimePeriodically(@RequestBody AppNamePeriodRequest appNamePeriodRequest, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = appFocusTimesService.updateFocusTimePeriodically(sessionUser.getEmail(), appNamePeriodRequest);

        return ResponseEntity.ok().body(response);
    }
}
