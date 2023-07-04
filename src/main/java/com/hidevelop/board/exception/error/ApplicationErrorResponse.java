package com.hidevelop.board.exception.error;

import com.hidevelop.board.exception.message.ApplicationErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationErrorResponse {

    private HttpStatus status;
    private String code;
    private String description;
    public static ResponseEntity<ApplicationErrorResponse> toResponseEntity(ApplicationErrorMessage errorMessage){
        return ResponseEntity.status(errorMessage.getStatus())
                .body(
                        ApplicationErrorResponse.builder()
                                .status(errorMessage.getStatus())
                                .code(errorMessage.name())
                                .description(errorMessage.getDescription())
                                .build()
                );
    }
}
