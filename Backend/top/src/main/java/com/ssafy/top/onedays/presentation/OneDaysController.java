package com.ssafy.top.onedays.presentation;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.onedays.dto.request.TimeGoalRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OneDaysController {

    private final OneDaysService oneDaysService;

    @GetMapping("/dash/stats/focus-time")
    public ResponseEntity<?> findTotalFocusTimeByPeriod(@RequestParam(name = "period") String period, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.findTotalFocusTimeByLoginIdAndPeriod(loginId, period);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/dash/stats/focus-time/detail")
    public ResponseEntity<CommonResponseDto<?>> findFocusTimeListByPeriod(@RequestParam(name = "period") String period, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.findFocusTimeListByLoginIdAndPeriod(loginId, period);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/dash/streak")
    public ResponseEntity<CommonResponseDto<?>> findFocusTimeListByMonth(@RequestParam(name="month") int month, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.findFocusTimeListByLoginIdAndMonth(loginId, month);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/dash/calendar")
    public ResponseEntity<?> findFocusTimeListByYearAndMonth(@RequestParam(name="year") int year, @RequestParam(name="month") int month, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.findFocusTimeListByLoginIdAndYearAndMonth(loginId, year, month);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/focus-time/goal")
    public ResponseEntity<?> findByTimeGoal(HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.findByLoginId(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/focus-time/goal")
    public ResponseEntity<?> saveTimeGoal(@RequestBody TimeGoalRequest timeGoal, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.saveTimeGoal(loginId, timeGoal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/focus-time/goal")
    public ResponseEntity<?> updateTimeGoal(@RequestBody TimeGoalRequest timeGoal, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        CommonResponseDto<?> response = oneDaysService.updateTimeGoal(loginId, timeGoal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
