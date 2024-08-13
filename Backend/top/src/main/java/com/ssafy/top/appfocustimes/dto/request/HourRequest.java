package com.ssafy.top.appfocustimes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "단위 시간 기준 집중 시간 추가 요청 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HourRequest {

    @Schema(description = "추가할 집중 단위 시간")
    private int hour;
}
