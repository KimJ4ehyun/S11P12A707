package com.ssafy.top.hourfocustimes.presentation;

import com.ssafy.top.appfocustimes.application.AppFocusTimesService;
import com.ssafy.top.appfocustimes.domain.AppFocusTimes;
import com.ssafy.top.appfocustimes.dto.response.AppListResponse;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.hourfocustimes.application.HourFocusTimesService;
import com.ssafy.top.hourfocustimes.dto.request.HourRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HourFocusTimesController {

    private final HourFocusTimesService hourFocusTimesService;

    @PostMapping("/focus-time")
    public ResponseEntity<?> save(@RequestBody HourRequest hourRequest, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hourFocusTimesService.save(loginId, hourRequest));
    }
}
