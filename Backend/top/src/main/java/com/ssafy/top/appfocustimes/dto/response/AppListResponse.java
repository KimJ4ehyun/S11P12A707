package com.ssafy.top.appfocustimes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "TOP 3 APP 집중시간 요청에 대한 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppListResponse {

    @Schema(description = "APP 이름")
    private String name;

    @Schema(description = "집중 시간")
    private int focusTime;

    @Schema(description = "집중률")
    private int focusRate;
}
