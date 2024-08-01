package com.ssafy.top.hourfocustimes.presentation;

import com.ssafy.top.hourfocustimes.application.HourFocusTimesService;
import com.ssafy.top.hourfocustimes.dto.request.FocusTimeRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ssafy.top.hourfocustimes.dto.request.HourRequest;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
public class HourFocusTimesController {

    private final HourFocusTimesService hourFocusTimesService;

    @PostMapping("/focus-time")
    public ResponseEntity<?> save(@RequestBody HourRequest hourRequest, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        loginId = "Timo1@gmail.com";

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hourFocusTimesService.save(loginId, hourRequest));
    }

    @PutMapping("/focus-time")
    public ResponseEntity<?> updateFocusTime(@RequestBody FocusTimeRequest focusTimeRequest, HttpSession session){
        String loginId = (String) session.getAttribute("loginId");
        loginId = "Timo1@gmail.com";
        return ResponseEntity.ok().body(hourFocusTimesService.updateFocusTime(loginId, focusTimeRequest));
    }
}
