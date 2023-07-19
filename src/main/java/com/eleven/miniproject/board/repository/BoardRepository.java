package com.eleven.miniproject.board.repository;

import com.eleven.miniproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByUser_Username(String username);

    @Query("select b from Board b " +
            "left join fetch b.user u " +
            "left join fetch b.image i " +
            "where b.id = :boardId and u.username = :username")
    Optional<Board> findBoardAndImageByIdAndUsername(@Param("boardId") Long boardId, @Param("username") String username);


    List<Board> findAllByOrderByModifiedAtDesc();

    List<Board> findTop5ByOrderByModifiedAtDesc();

    List<Board> findTop5ByUser_UsernameOrderByModifiedAtDesc(String username);
}
