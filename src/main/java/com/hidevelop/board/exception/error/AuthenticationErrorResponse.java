package com.hidevelop.board.exception.error;

import com.hidevelop.board.exception.message.AuthErrorMessage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class AuthenticationErrorResponse {

    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<AuthenticationErrorResponse> toResponseEntity(AuthErrorMessage authErrorMessage) {
        return ResponseEntity.status(authErrorMessage.getStatus())
                .body(AuthenticationErrorResponse.builder()
                        .status(authErrorMessage.getStatus().value())
                        .error(authErrorMessage.getStatus().name())
                        .code(authErrorMessage.name())
                        .message(authErrorMessage.getErrorMessage())
                        .build());
    }
}
