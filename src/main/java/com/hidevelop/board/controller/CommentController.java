package com.hidevelop.board.controller;

import com.hidevelop.board.model.dto.CommentDto;
import com.hidevelop.board.service.Impl.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;
    @PostMapping("/{boardId}/comment")
    public ResponseEntity<?> saveComment(@PathVariable(value = "boardId") Long boardId,
                                         @RequestBody CommentDto.Request request,
                                         Principal principal){
        var result = commentService.saveComment(request, principal.getName(), boardId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{boardId}/comment")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "boardId") Long boardId,
                                           @RequestParam Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
