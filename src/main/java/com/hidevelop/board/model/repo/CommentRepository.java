package com.hidevelop.board.model.repo;

import com.hidevelop.board.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsById(Long commentId);

    List<Comment> findAllByBoardIdOrderByIdDesc(Long boardId);
}
