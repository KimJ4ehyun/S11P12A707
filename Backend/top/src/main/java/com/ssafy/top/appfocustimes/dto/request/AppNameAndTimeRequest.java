package com.ssafy.top.appfocustimes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "스톱워치를 사용한 APP 집중 시간 저장 요청 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppNameAndTimeRequest {

    @Schema(description = "집중한 APP 이름")
    private String appName;

    @Schema(description = "해당 APP에 집중한 시간")
    private int focusTime;
}
