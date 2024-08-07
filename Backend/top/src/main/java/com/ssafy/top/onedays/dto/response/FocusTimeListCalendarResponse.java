package com.ssafy.top.onedays.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "캘린더 집중 시간 통계 조회 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FocusTimeListCalendarResponse {

    @Schema(description = "해당되는 일자")
    private int day;

    @Schema(description = "해당 일자의 집중 시간")
    private int focusTime;
}
