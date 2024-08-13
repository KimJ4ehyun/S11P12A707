package com.ssafy.top.onedays.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "목표 시간 조회 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TimeGoalAndDayResponse {

    @Schema(description = "해당 날짜")
    private String day;

    @Schema(description = "해당 날짜의 목표 시간")
    private int timeGoal;
}
