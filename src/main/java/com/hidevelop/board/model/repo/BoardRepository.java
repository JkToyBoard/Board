package com.hidevelop.board.model.repo;

import com.hidevelop.board.model.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);

    @Transactional
    void deleteById(Long id);

    boolean existsById(Long id);
}
