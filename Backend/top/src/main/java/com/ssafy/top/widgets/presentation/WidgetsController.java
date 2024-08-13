package com.ssafy.top.widgets.presentation;

import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.users.dto.response.UsersResponse;
import com.ssafy.top.widgets.application.WidgetsService;
import com.ssafy.top.widgets.dto.WidgetsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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

    @Operation(summary = "위젯 조회",
            description = "위젯을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "위젯 조회 성공",
                    content = @Content(schema = @Schema(implementation = WidgetsDto.class))),
    })
    @GetMapping
    public ResponseEntity<?> getWidgets(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = widgetsService.getWidgets(sessionUser.getEmail());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "위젯 저장",
            description = "위젯을 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "위젯 저장 성공")
    })
    @PostMapping
    public ResponseEntity<?> saveWidgets(HttpSession session, @RequestBody List<WidgetsDto> widgets) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = widgetsService.saveWidgets(sessionUser.getEmail(), widgets);

        return ResponseEntity.ok(response);
    }
}
