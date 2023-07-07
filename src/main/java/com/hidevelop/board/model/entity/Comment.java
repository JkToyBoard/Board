package com.hidevelop.board.model.entity;

import com.hidevelop.board.model.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long boardId;
    private String content;
    private String writer;

    public CommentDto.Response of(){
        return CommentDto.Response.builder()
                .content(this.content)
                .writer(this.writer)
                .updateAt(this.getUpdatedAt())
                .build();
    }
}
