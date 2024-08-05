package com.ssafy.top.hourfocustimes.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HourFocusTimeSumResponse {

    private Long userId;
    private Long focusTimeSum;
}
