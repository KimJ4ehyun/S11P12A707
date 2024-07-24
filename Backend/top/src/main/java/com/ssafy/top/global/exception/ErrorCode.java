package com.ssafy.top.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    WHITESPACE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "공백은 사용할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
