package com.ssafy.top.onedays.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "목표 시간 추가/수정 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TimeGoalResponse {

    @Schema(description = "설정할 목표 시간")
    private int timeGoal;
}
