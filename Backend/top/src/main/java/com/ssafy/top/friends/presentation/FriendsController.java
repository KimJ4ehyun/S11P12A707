package com.ssafy.top.friends.presentation;

import com.ssafy.top.friends.application.FriendsService;
import com.ssafy.top.friends.dto.response.FriendsResponse;
import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

@Tag(name = "친구 관련 API", description = "친구에 관련된 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendsController {
    private final FriendsService friendsService;

    @Operation(summary = "친구 목록 조회",
            description = "친구 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "친구 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = FriendsResponse.class)))
    })
    @GetMapping
    public ResponseEntity<?> findFriends(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<List<FriendsResponse>> response = friendsService.getFriends(sessionUser.getEmail());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "친구 신청",
            description = "friendId로 친구 신청을 보낸다.")
    @Parameters(value = {
            @Parameter(name = "friendId", description = "친구 신청을 보낼 유저의 아이디")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "친구 신청 성공"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 실패(존재하지 않는 사용자에게 친구 신청)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 신청 실패(자기 자신에게 친구 신청)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 신청 실패(이미 친구 신청 중)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 신청 실패(이미 친구)")
    })
    @PostMapping("/request/{friendId}")
    public ResponseEntity<?> requestFriends(HttpSession session, @PathVariable Long friendId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = friendsService.requestFriends(sessionUser.getEmail(), friendId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "친구 신청 수락",
            description = "friendId가 보낸 친구 신청을 수락한다.")
    @Parameters(value = {
            @Parameter(name = "friendId", description = "친구 신청을 보낸 유저의 아이디")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "친구 신청 수락 성공"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 수락 실패(존재하지 않는 사용자에게 온 친구 신청)"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 수락 실패(존재하지 않는 친구 신청)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 신청 수락 실패(이미 친구)")
    })
    @PostMapping("/response/{friendId}")
    public ResponseEntity<?> responseFriends(HttpSession session, @PathVariable Long friendId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = friendsService.responseFriends(sessionUser.getEmail(), friendId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "친구 신청 거절",
            description = "friendId가 보낸 친구 신청을 거절한다.")
    @Parameters(value = {
            @Parameter(name = "friendId", description = "친구 신청을 보낸 유저의 아이디")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "친구 신청 거절 성공"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 거절(존재하지 않는 사용자에게 온 친구 신청)"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 거절(존재하지 않는 친구 신청)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 신청 거절(이미 친구)")
    })
    @DeleteMapping("/response/{friendId}")
    public ResponseEntity<?> declineFriends(HttpSession session, @PathVariable Long friendId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = friendsService.declineFriends(sessionUser.getEmail(), friendId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "친구 신청 취소",
            description = "friendId에게 보낸 친구 신청을 취소한다.")
    @Parameters(value = {
            @Parameter(name = "friendId", description = "친구 신청을 보낸 유저의 아이디")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "친구 신청 취소 성공"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 취소(존재하지 않는 사용자에게 온 친구 신청)"),
            @ApiResponse(responseCode = "404",
                    description = "친구 신청 취소(존재하지 않는 친구 신청)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 신청 취소(이미 친구)")
    })
    @DeleteMapping("/request/{friendId}")
    public ResponseEntity<?> cancelFriends(HttpSession session, @PathVariable Long friendId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = friendsService.cancelFriends(sessionUser.getEmail(), friendId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "친구 삭제",
            description = "friendId와의 친구 관계를 삭제한다.")
    @Parameters(value = {
            @Parameter(name = "friendId", description = "친구 아이디")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "친구 삭제 성공"),
            @ApiResponse(responseCode = "404",
                    description = "친구 삭제 실패(존재하지 않는 사용자와의 친구 삭제)"),
            @ApiResponse(responseCode = "404",
                    description = "친구 삭제 실패(존재하지 않는 친구 관계)"),
            @ApiResponse(responseCode = "400",
                    description = "친구 삭제 실패(이미 친구)")
    })
    @DeleteMapping("/{friendId}")
    public ResponseEntity<?> deleteFriends(HttpSession session, @PathVariable Long friendId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<?> response = friendsService.deleteFriends(sessionUser.getEmail(), friendId);

        return ResponseEntity.ok(response);
    }
}
