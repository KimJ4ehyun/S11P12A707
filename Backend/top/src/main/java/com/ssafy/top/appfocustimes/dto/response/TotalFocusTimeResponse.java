package com.ssafy.top.appfocustimes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "전체 집중 시간 조회에 대한 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TotalFocusTimeResponse {

    @Schema(description = "전체 집중 시간")
    private String totalFocusTime;
}
