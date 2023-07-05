package com.hidevelop.board.model.entity;

import com.hidevelop.board.model.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String writer;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    @OneToOne(orphanRemoval = true)
    private ViewCount viewCount;


    public BoardDto.Response Of(){
        return BoardDto.Response.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .images(this.images)
                .viewCount(this.getViewCount().getViewCount())
                .build();
    }

    public void update(BoardDto.UpdateRequest request, List<String> images) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.images = images;
    }
}
