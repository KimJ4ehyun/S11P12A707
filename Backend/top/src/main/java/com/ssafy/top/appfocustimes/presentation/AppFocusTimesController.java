package com.ssafy.top.appfocustimes.presentation;

import com.ssafy.top.appfocustimes.application.AppFocusTimesService;
import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.dto.request.AppNameRequest;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppFocusTimesController {

    private final AppFocusTimesService appFocusTimesService;

    @GetMapping("/dash/stats/app")
    public ResponseEntity<?> findAppFocusTimeList(HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        loginId = "Timo1@gmail.com";
        List<AppFocusTimes> AppFocusTimesList = appFocusTimesService.findAppFocusTimesByLoginId(loginId);
        AppListResponse[] appListResponses = appFocusTimesService.findTopThreeByAppFocusTimeList(AppFocusTimesList);
        CommonResponseDto<Object> response = new CommonResponseDto<>(appListResponses, "프로그램 별 통계 조회에 성공했습니다.", 200);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/focus-time/app")
    public ResponseEntity<?> save(@RequestBody AppNameRequest appNameRequest, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        loginId = "Timo1@gmail.com";

        CommonResponseDto<?> response = appFocusTimesService.save(loginId, appNameRequest);

        if (response.getStatusCode() == 201) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.ok().body(response);
        }
    }
}
