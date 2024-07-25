package com.ssafy.top.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    FRIEND_SELF_REQUEST(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 신청할 수 없습니다."),
    FRIEND_ALREADY_REQUESTING(HttpStatus.BAD_REQUEST, "이미 친구 신청이 진행중입니다."),
    FRIEND_ALREADY_ADDED(HttpStatus.BAD_REQUEST, "이미 친구입니다."),
    WHITESPACE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "공백은 사용할 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
