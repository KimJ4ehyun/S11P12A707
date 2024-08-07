package com.ssafy.top.hourfocustimes.dao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HourFocusTimeSumDao {

    private Long userId;
    private Long focusTimeSum;
}
