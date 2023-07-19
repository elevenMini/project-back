package com.eleven.miniproject.board.repository;

import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.Image;
import com.eleven.miniproject.board.entity.UploadImage;
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
    private ImageRepository imageRepository;

    @Autowired
    EntityManager em;

    @Test
    public void boardSelectOneTest() throws Exception {

        // given
        User user = new User("ghdtmdgus123", "asdf1234");
        UploadImage uploadImage = new UploadImage("aaa", "aaa");
        Image image = new Image(uploadImage);
        Board board = new Board(user, "test", "aaa", image);
        userRepository.save(user);
        imageRepository.save(image);
        boardRepository.save(board);
        em.flush();
        em.clear();

        // when
        Board findBoard = boardRepository.findBoardAndImageByIdAndUsername(board.getId(), board.getUser().getUsername()).get();
        System.out.println("findBoard.getId() = " + findBoard.getId());
        System.out.println("findBoard.getImage().getId() = " + findBoard.getImage().getId());
        System.out.println("findBoard.getImage().getUploadImage().getStoreFileName() = " + findBoard.getImage().getUploadImage().getStoreFileName());
        System.out.println("findBoard.getUser().getUsername() = " + findBoard.getUser().getUsername());

        // then
//        Assertions.assertThat(findBoard.getId()).isSameAs(board.getId());
    }
}