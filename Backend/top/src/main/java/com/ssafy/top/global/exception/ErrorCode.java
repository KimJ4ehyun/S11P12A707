package com.ssafy.top.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 닉네임입니다."),

    FRIEND_SELF_REQUEST(HttpStatus.BAD_REQUEST, "자기 자신에게 친구 신청할 수 없습니다."),
    FRIEND_ALREADY_REQUESTING(HttpStatus.BAD_REQUEST, "이미 친구 신청이 진행중입니다."),
    FRIEND_ALREADY_ADDED(HttpStatus.BAD_REQUEST, "이미 친구입니다."),
    FRIEND_REQUEST_MISSING(HttpStatus.NOT_FOUND, "존재하지 않는 친구 신청입니다."),

    WHITESPACE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "공백은 사용할 수 없습니다."),
    INVALID_TIME_GOAL(HttpStatus.BAD_REQUEST, "목표 시간 형식이 잘못되었습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "조건에 맞는 데이터를 찾을 수 없습니다."),
    INVALID_QUERY_STRING(HttpStatus.BAD_REQUEST, "쿼리 스트링이 잘못 입력되었습니다."),
    ONE_DAY_NOT_FOUND(HttpStatus.NOT_FOUND, "하루 데이터를 찾을 수 없습니다."),
    HOUR_FOCUS_TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "시간 단위 집중 시간 데이터를 찾을 수 없습니다."),
    ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 데이터가 이미 존재합니다."),
    INVALID_DOMAIN(HttpStatus.BAD_REQUEST, "도메인 형식이 올바르지 않습니다."),
    BAN_ALREADY_ADDED(HttpStatus.CONFLICT, "이미 금지 목록에 포함되어 있습니다."),
    INVALID_APP_NAME(HttpStatus.BAD_REQUEST, "앱 이름은 null일 수 없습니다."),
    INVALID_URL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 url입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
