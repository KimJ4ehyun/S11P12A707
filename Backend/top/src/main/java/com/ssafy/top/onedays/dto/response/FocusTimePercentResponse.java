package com.ssafy.top.onedays.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FocusTimePercentResponse {

    private int dayPercent;
    private int weekPercent;
    private int monthPercent;
}
