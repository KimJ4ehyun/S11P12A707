package com.ssafy.top.onedays.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PeriodTotalFocusTimeResponse {

    private String totalFocusTime;
    private String LastTotalFocusTime;
}