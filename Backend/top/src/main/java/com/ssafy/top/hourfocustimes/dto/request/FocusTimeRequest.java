package com.ssafy.top.hourfocustimes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "단위 집중시간 업데이트 요청 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FocusTimeRequest {

    @Schema(description = "업데이트 할 누적된 집중시간")
    private int focusTime;
}
