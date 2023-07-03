package com.hidevelop.board.model.dto;

import lombok.Builder;
import lombok.Getter;

public class BoardDto {

    @Getter
    public static class Request{
        private String title;
        private String content;
    }
}
