package com.ssafy.top.users.presentation;

import com.ssafy.top.global.auth.domain.SessionUser;
import com.ssafy.top.global.domain.CommonResponseDto;
import com.ssafy.top.users.application.UsersService;
import com.ssafy.top.users.dto.request.UserUpdateRequest;
import com.ssafy.top.users.dto.response.UserResponse;
import com.ssafy.top.users.dto.response.UserUpdateResponse;
import com.ssafy.top.users.dto.response.UsersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 관련 API", description = "사용자에 관련된 API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "유저 검색",
            description = "친구를 추가하기 위해 nickname으로 유저를 검색한다.")
    @Parameters(value = {
            @Parameter(name = "nickname", description = "검색할 유저의 닉네임")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "유저 검색 성공",
                    content = @Content(schema = @Schema(implementation = UsersResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "유저 검색 실패(검색어가 비었습니다)")
    })
    @GetMapping("/users")
    public ResponseEntity<?> findUsersByNickname(HttpSession session, @RequestParam String nickname) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<List<UsersResponse>> response = usersService.getUsersByLoginId(sessionUser.getEmail(), nickname);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 정보 조회",
            description = "현재 로그인 한 유저의 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "내 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "내 정보 조회 실패(없는 유저)")
    })
    @GetMapping("/user")
    public ResponseEntity<?> findUser(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<UserResponse> response = usersService.getUser(sessionUser.getEmail());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 정보 수정",
            description = "현재 로그인 한 유저의 정보를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "내 정보 수정 성공",
                    content = @Content(schema = @Schema(implementation = UserUpdateResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "내 정보 수정 실패(없는 유저)"),
            @ApiResponse(responseCode = "409",
                    description = "중복된 닉네임"),
            @ApiResponse(responseCode = "400",
                    description = "공백인 닉네임")
    })
    @PutMapping("/user")
    public ResponseEntity<?> updateUser(HttpSession session, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<UserUpdateResponse> response = usersService.updateUser(sessionUser.getEmail(), userUpdateRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "화면 활성화 여부 수정",
            description = "화면 활성화 여부를 수정한다.")
    @Parameters(value = {
            @Parameter(name = "isShare", description = "true or false")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "화면 활성화 여부 업데이트 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "존재하지 않는 유저")
    })
    @PutMapping("/screenShare/{isShare}")
    public ResponseEntity<?> screenShare(HttpSession session, @PathVariable Boolean isShare) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<Boolean> response = usersService.screenShare(sessionUser.getEmail(), isShare);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "익스텐션 활성화 여부 수정",
            description = "익스텐션 활성화 여부를 수정한다.")
    @Parameters(value = {
            @Parameter(name = "isActive", description = "true or false")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "익스텐션 활성화 여부 업데이트 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "존재하지 않는 유저")
    })
    @PutMapping("/extension/{isActive}")
    public ResponseEntity<?> extension(HttpSession session, @PathVariable Boolean isActive) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        CommonResponseDto<Boolean> response = usersService.extension(sessionUser.getEmail(), isActive);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/check")
    public ResponseEntity<?> login(HttpSession session){
        Object attribute = session.getAttribute("user");
        log.info("{}", attribute);
        CommonResponseDto<Boolean> response = new CommonResponseDto<>(true, "로그인된 상태", 200);
        if(attribute==null) {
            response = new CommonResponseDto<>(false, "로그인 필요", 200);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/login")
    public ResponseEntity<?> login(){
        CommonResponseDto<Boolean> response = new CommonResponseDto<>(true, "로그인 성공", 200);
        return ResponseEntity.ok(response);
    }
}
