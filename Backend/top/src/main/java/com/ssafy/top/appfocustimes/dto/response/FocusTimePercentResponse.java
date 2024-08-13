package com.ssafy.top.appfocustimes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "일간/주간/월간 백분율 조회 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FocusTimePercentResponse {

    @Schema(description = "일간 백분율")
    private int dayPercent;

    @Schema(description = "주간 백분율")
    private int weekPercent;

    @Schema(description = "월간 백분율")
    private int monthPercent;
}
