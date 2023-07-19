package com.hidevelop.board.exception.error;

import com.hidevelop.board.exception.message.AuthErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationException extends RuntimeException{
    private final AuthErrorMessage authErrorMessage;
}
