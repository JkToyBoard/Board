package com.hidevelop.board.model.dto;

import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.ViewCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

        public Board toEntity( ViewCount viewCount, String writer){
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .viewCount(viewCount)
                    .writer(writer)
                    .build();
        }
    }

    @Getter
    public static class UpdateRequest {
        private Long id;
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class simpleBoard{
        private Long id;
        private String title;
        private String writer;
        private LocalDateTime updateAt;
        private Long viewCount;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private List<String> images;
        private Long viewCount;
        private Set<CommentDto.Response> comments;
    }


}
