package com.hidevelop.board.service.Impl;

import com.hidevelop.board.exception.error.ApplicationException;
import com.hidevelop.board.exception.error.AuthenticationException;
import com.hidevelop.board.exception.message.ApplicationErrorMessage;
import com.hidevelop.board.exception.message.AuthErrorMessage;
import com.hidevelop.board.model.dto.CommentDto;
import com.hidevelop.board.model.entity.Board;
import com.hidevelop.board.model.entity.Comment;
import com.hidevelop.board.model.repo.BoardRepository;
import com.hidevelop.board.model.repo.CommentRepository;
import com.hidevelop.board.model.repo.UserRepository;
import com.hidevelop.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    public CommentDto.Response saveComment(CommentDto.Request request, String username, Long boardId){
        userRepository.findByUsername(username)
                .orElseThrow( () -> new AuthenticationException(AuthErrorMessage.USER_NOT_FOUND));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_BOARD));

        Comment comment = commentRepository.save(request.toEntity(username, board.getId()));

        board.addComment(comment);
        boardRepository.save(board);

        return comment.of();
    }

    public void deleteComment(Long commentId) {

        if(!commentRepository.existsById(commentId)){
            throw new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_COMMENT);
        }
        commentRepository.deleteById(commentId);
    }

    public List<CommentDto.Response> readComment(Long boardId) {
        boolean exists = boardRepository.existsById(boardId);
        if (!exists){
            throw new ApplicationException(ApplicationErrorMessage.NOT_REGISTERED_BOARD);
        }
        List<Comment> comments = commentRepository.findAllByBoardIdOrderByIdDesc(boardId);
        List<CommentDto.Response> responses = comments.stream().map(m -> m.of()).collect(Collectors.toList());
        return responses;
    }
}
