package com.hidevelop.board.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationErrorMessage {

    DUPLICATE_APPLICATION(HttpStatus.BAD_REQUEST, "이미 나눔을 신청하셨습니다."),
    NOT_REGISTERED_USER(HttpStatus.BAD_REQUEST,"등록되지않은 사용자입니다."),
    NOT_REGISTERED_GOOD(HttpStatus.BAD_REQUEST,"등록되지않은 상품입니다."),
    NOT_REGISTERED_APPLICATION(HttpStatus.BAD_REQUEST,"등록되지 않은 신청입니다."),
    NOT_VALID_ADDRESS(HttpStatus.BAD_REQUEST,"올바른 주소방식이 아닙니다."),
    NOT_EXIST_IMAGE(HttpStatus.BAD_REQUEST, "찾을 수 없는 이미지입니다.")
            ;

    private final HttpStatus status;

    private final String description;
}
