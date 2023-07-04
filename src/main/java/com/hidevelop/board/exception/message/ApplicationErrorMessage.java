package com.hidevelop.board.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApplicationErrorMessage {


    NOT_REGISTERED_BOARD(HttpStatus.BAD_REQUEST,"등록되지않은 글입니다."),
    NOT_REGISTERED_COMMENT(HttpStatus.BAD_REQUEST,"등록되지않은 댓글입니다."),
    ;

    private final HttpStatus status;

    private final String description;
}
