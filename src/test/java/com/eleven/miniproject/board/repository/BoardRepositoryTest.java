package com.eleven.miniproject.board.repository;

import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.user.entity.User;
import com.eleven.miniproject.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    public void boardSelectOneTest() throws Exception {

        // given
        User user = new User("ghdtmdgus123", "asdf1234");
        Board board = new Board(user, "test", "aaa", null);

        userRepository.save(user);
        boardRepository.save(board);
        em.flush();
        em.clear();

        // when
        Board findBoard = boardRepository.findByIdAndUser(board.getId()).get();

        // then
        Assertions.assertThat(findBoard.getId()).isSameAs(board.getId());
    }
}