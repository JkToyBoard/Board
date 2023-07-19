package com.hidevelop.board.service;


import com.hidevelop.board.model.dto.CommentDto;
import java.util.List;


public interface CommentService {

    CommentDto.Response saveComment(CommentDto.Request request, String username, Long boardId);
    void deleteComment(Long commentId);
    List<CommentDto.Response> readComment(Long boardId);
}
