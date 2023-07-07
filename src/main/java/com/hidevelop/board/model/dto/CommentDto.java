package com.hidevelop.board.model.dto;

import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    public static class Request{

        private String content;


        public Comment toEntity(String username, Long boardId){
            return Comment.builder()
                    .boardId(boardId)
                    .content(this.content)
                    .writer(username)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private Long id;
        private String writer;
        private String content;
        private LocalDateTime updateAt;

    }
}
