package com.hidevelop.board.exception.error;

import com.hidevelop.board.exception.message.ApplicationErrorMessage;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    private ApplicationErrorMessage errorMessage;
    private String description;

    public ApplicationException(ApplicationErrorMessage errorMessage){
        this.errorMessage = errorMessage;
        this.description = errorMessage.getDescription();
    }
}
