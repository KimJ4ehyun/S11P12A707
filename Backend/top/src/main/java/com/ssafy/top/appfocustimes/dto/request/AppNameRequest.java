package com.ssafy.top.appfocustimes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "화면 전환 시 APP 집중 시간 저장 요청 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppNameRequest {

    @Schema(description = "이전에 사용한 APP 이름")
    private String appName;

    @Schema(description = "지금 사용하고 있는 APP 이름")
    private String startTime;

    @Schema(description = "지금 사용하고 있는 APP 이름")
    private String endTime;
}
