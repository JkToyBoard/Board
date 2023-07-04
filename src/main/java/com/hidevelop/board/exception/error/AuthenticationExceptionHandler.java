package com.hidevelop.board.exception.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static com.hidevelop.board.exception.message.AuthErrorMessage.USER_NOT_FOUND;

@Slf4j
@RestController
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<AuthenticationErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", USER_NOT_FOUND);
        return AuthenticationErrorResponse.toResponseEntity(USER_NOT_FOUND);
    }

    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<AuthenticationErrorResponse> handleCustomException(AuthenticationException e) {
        log.error("handleCustomException throw CustomException : {}", e.getAuthErrorMessage());
        return AuthenticationErrorResponse.toResponseEntity(e.getAuthErrorMessage());
    }
}
