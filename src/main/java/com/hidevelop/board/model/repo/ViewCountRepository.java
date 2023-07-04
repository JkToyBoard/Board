package com.hidevelop.board.model.repo;

import com.hidevelop.board.model.entity.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewCountRepository extends JpaRepository<ViewCount,Long> {

}
