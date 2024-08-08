package com.ssafy.top.widgets.presentation;

import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.widgets.application.WidgetsService;
import com.ssafy.top.widgets.dto.WidgetsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "위젯 API", description = "위젯에 관련된 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/widgets")
public class WidgetsController {
    private final WidgetsService widgetsService;

    @Operation(summary = "위젯 저장",
            description = "위젯을 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "위젯 저장 성공")
    })
    @PostMapping
    public ResponseEntity<?> saveWidgets(@RequestBody List<WidgetsDto> widgets) {
        Long userId = 1L;

        CommonResponseDto<?> response = widgetsService.saveWidgets(userId, widgets);

        return ResponseEntity.ok(response);
    }
}
