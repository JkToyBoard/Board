package com.hidevelop.board.model.repo;

import com.hidevelop.board.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoardIdOrderByIdDesc(Long BoardId);
    boolean existsById(Long commentId);
}
