package com.hidevelop.board.model.dto;

import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.User;
import com.hidevelop.board.model.entity.ViewCount;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class BoardDto {

    @Getter
    public static class Request{
        private String title;
        private String content;

        public Board toEntity(List<String> images, ViewCount viewCount, String writer){
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .images(images)
                    .viewCount(viewCount)
                    .writer(writer)
                    .build();
        }
    }
}
