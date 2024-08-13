package com.ssafy.top.appfocustimes.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FocusTimeSumResponse implements Comparable<FocusTimeSumResponse>{

    private Long userId;
    private Long focusTimeSum;

    public void updateFocusTimeSum(Long focusTimeSum) {
        this.focusTimeSum = focusTimeSum;
    }

    @Override
    public int compareTo(FocusTimeSumResponse o) {
        return (o.focusTimeSum >= this.focusTimeSum) ? 1 : -1;
    }
}
