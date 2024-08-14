package com.ssafy.top.appfocustimes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "통계 그래프에 사용되는 집중 시간 조회에 대한 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FocusTimeListDayResponse {

    @Schema(description = "단위 시간")
    private int startTime;

    @Schema(description = "집중 시간")
    private long focusTime;
}
