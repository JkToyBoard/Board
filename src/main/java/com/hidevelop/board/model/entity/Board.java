package com.hidevelop.board.model.entity;

import com.amazonaws.services.ec2.model.FederatedAuthentication;
import com.hidevelop.board.model.dto.BoardDto;
import com.hidevelop.board.model.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

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

//    @Enumerated
//    private Long viewCount;
    @OneToOne(orphanRemoval = true)
    private ViewCount viewCount;

    @OneToMany(mappedBy = "boardId" , fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

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

    public BoardDto.Response Of(List<CommentDto.Response> comments){
        return BoardDto.Response.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .images(this.images)
                .viewCount(this.getViewCount().getViewCount())
                .comments(comments)
                .build();
    }

    public void update(BoardDto.UpdateRequest request, List<String> images) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.images = images;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}
