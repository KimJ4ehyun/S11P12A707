package com.ssafy.top.widgets.dto;

import com.ssafy.top.users.domain.Users;
import com.ssafy.top.widgets.domain.WidgetType;
import com.ssafy.top.widgets.domain.Widgets;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "위젯 상세 정보 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WidgetsDto {
    @Schema(description = "위젯 이름")
    private String name;

    @Schema(description = "위젯 너비")
    private Integer width;

    @Schema(description = "위젯 높이")
    private Integer height;

    @Schema(description = "위젯 좌상단 x 좌표")
    private Integer x;

    @Schema(description = "위젯 좌상단 y 좌표")
    private Integer y;

    public static WidgetsDto toDto(Widgets widget) {
        return WidgetsDto.builder()
                .name(String.valueOf(widget.getName()))
                .width(widget.getWidth())
                .height(widget.getHeight())
                .x(widget.getX())
                .y(widget.getY())
                .build();
    }

    public Widgets toEntity(Users user) {
        return Widgets.builder()
                .name(WidgetType.valueOf(name))
                .x(x)
                .y(y)
                .width(width)
                .height(height)
                .user(user)
                .build();
    }
}
