package com.ssafy.top.appfocustimes.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "모든 APP 집중 시간 조회 요청에 대한 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppAndTimeDao {

    @Schema(description = "APP 이름")
    private String appName;

    @Schema(description = "집중 시간")
    private Long focusTime;
}
