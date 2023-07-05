package com.hidevelop.board.service.Impl;

import com.hidevelop.board.exception.error.ApplicationException;
import com.hidevelop.board.exception.error.AuthenticationException;
import com.hidevelop.board.exception.message.ApplicationErrorMessage;
import com.hidevelop.board.exception.message.AuthErrorMessage;
import com.hidevelop.board.model.dto.CommentDto;
import com.hidevelop.board.model.entity.Comment;
import com.hidevelop.board.model.repo.BoardRepository;
import com.hidevelop.board.model.repo.CommentRepository;
import com.hidevelop.board.model.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    public CommentDto.Response saveComment(CommentDto.Request request, String username, Long boardId){
        userRepository.findByUsername(username)
                .orElseThrow( () -> new AuthenticationException(AuthErrorMessage.USER_NOT_FOUND));
        boardRepository.findById(boardId)
                .orElseThrow( () -> new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_BOARD));
        Comment entity = request.toEntity(username, boardId);

        Comment comment = commentRepository.save(entity);

        return comment.of();
    }

    public void deleteComment(Long commentId) {

        if(!commentRepository.existsById(commentId)){
            throw new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_COMMENT);
        }
        commentRepository.deleteById(commentId);
    }
}
