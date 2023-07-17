package com.eleven.miniproject.board.repository;

import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByUser_Username(String username);
}
