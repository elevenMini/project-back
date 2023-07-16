package com.eleven.miniproject.board.createBoard.repository;

import com.eleven.miniproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCreateRepository extends JpaRepository<Board, Long> {
}
