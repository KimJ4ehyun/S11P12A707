package com.ssafy.top.appfocustimes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "일간/주간/월간 집중시간 통계 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PeriodTotalFocusTimeResponse {

    @Schema(description = "최근 총 집중 시간")
    private String totalFocusTime;

    @Schema(description = "저번 총 집중 시간")
    private String LastTotalFocusTime;
}
