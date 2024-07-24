package com.ssafy.top.onedays.presentation;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.onedays.application.OneDaysService;
import com.ssafy.top.onedays.dto.response.FocusTimeListResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/dash")
@RestController
public class OneDaysController {

    private final OneDaysService oneDaysService;

    @GetMapping("/stats/focus-time")
    public ResponseEntity<CommonResponseDto<String>> findTotalFocusTimeByPeriod(@RequestParam(name = "period") String period, HttpSession session) {
        if (!(period.equals("day") || period.equals("week") || period.equals("month"))) {
            CommonResponseDto<String> response = new CommonResponseDto<>("잘못된 쿼리 스트링입니다. - period", 400);
            return ResponseEntity.badRequest().body(response);
        }
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            CommonResponseDto<String> response = new CommonResponseDto<>("유저 정보가 없습니다.", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String totalFocusTime = oneDaysService.findTotalFocusTimeByLoginIdAndPeriod(loginId, period);
        CommonResponseDto<String> response = new CommonResponseDto<>(totalFocusTime, "집중시간 통계 조회에 성공했습니다.", 200);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/stats/focus-time/detail")
    public ResponseEntity<CommonResponseDto<?>> findFocusTimeListByPeriod(@RequestParam(name = "period") String period, HttpSession session) {
        if (!(period.equals("day") || period.equals("week") || period.equals("month"))) {
            CommonResponseDto<Object> response = new CommonResponseDto<>("잘못된 쿼리 스트링입니다. - period", 400);
            return ResponseEntity.badRequest().body(response);
        }
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            CommonResponseDto<Object> response = new CommonResponseDto<>("유저 정보가 없습니다.", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        Object data = oneDaysService.findFocusTimeListByLoginIdAndPeriod(loginId, period);
        CommonResponseDto<Object> response = new CommonResponseDto<>(data, "집중시간 통계 조회에 성공했습니다.", 200);
        return ResponseEntity.ok().body(response);
    }
}
