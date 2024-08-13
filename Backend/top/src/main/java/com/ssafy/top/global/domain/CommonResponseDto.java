package com.ssafy.top.global.domain;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonResponseDto<T> {

    private T data;
    private String message;
    private int statusCode;

    public CommonResponseDto(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
