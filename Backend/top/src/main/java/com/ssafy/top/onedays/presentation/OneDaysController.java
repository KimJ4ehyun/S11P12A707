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

@RestController
@RequestMapping("/dash")
@RequiredArgsConstructor
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
            CommonResponseDto<String> response = new CommonResponseDto<>("잘못된 쿼리 스트링입니다. - period", 400);
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

    @GetMapping("/streak")
    public ResponseEntity<CommonResponseDto<?>> findFocusTimeListByMonth(@RequestParam(name="month") int month, HttpSession session){

        if(!(month == 1 || month == 6)){
            CommonResponseDto<CommonResponseDto<Object>> response = new CommonResponseDto<>("쿼리 스트링(month)이 잘못 입력되었습니다.", 400);
            return ResponseEntity.badRequest().body(response);
        }
        String loginId = (String) session.getAttribute("loginId");
        if (loginId == null) {
            CommonResponseDto<Object> response = new CommonResponseDto<>("유저 정보가 없습니다.", 401);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        FocusTimeListResponse[] data = (FocusTimeListResponse[])oneDaysService.findFocusTimeListByLoginIdAndMonth(loginId, month);
        CommonResponseDto<FocusTimeListResponse[]> response = new CommonResponseDto<>(data, "스트릭 데이터 조회에 성공했습니다.", 200);
        return ResponseEntity.ok().body(response);
    }
}
