package com.eleven.miniproject.board.repository;

import com.eleven.miniproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByUser_Username(String username);

    @Query("select b from Board b where b.id = :boardId")
    Optional<Board> findByIdAndUser(@Param("boardId") Long boardId);
}
