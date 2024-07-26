package com.ssafy.top.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    FRIEND_SELF_REQUEST(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 신청할 수 없습니다."),
    FRIEND_ALREADY_REQUESTING(HttpStatus.BAD_REQUEST, "이미 친구 신청이 진행중입니다."),
    FRIEND_ALREADY_ADDED(HttpStatus.BAD_REQUEST, "이미 친구입니다."),
    FRIEND_REQUEST_MISSING(HttpStatus.NOT_FOUND, "존재하지 않는 친구 신청입니다."),
    WHITESPACE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "공백은 사용할 수 없습니다."),
    INVALID_TIME_GOAL(HttpStatus.BAD_REQUEST, "목표 시간 형식이 잘못되었습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "조건에 맞는 데이터를 찾을 수 없습니다."),
    INVALID_QUERY_STRING(HttpStatus.BAD_REQUEST, "쿼리 스트링이 잘못 입력되었습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
