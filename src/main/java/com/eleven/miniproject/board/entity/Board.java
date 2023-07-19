package com.eleven.miniproject.board.entity;


import com.eleven.miniproject.global.entity.Timestamped;
import com.eleven.miniproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    public Board(User findUser, String title, String content, Image image) {
        this.title = title;
        this.content = content;
        this.user = findUser;
        findUser.getBoards().add(this);
        this.image = image;
    }

    public void updateBoardEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 이미지가 없던 게시판에 이미지를 추가하는 메서드(overloading)
    public void updateBoardEntity(String title, String content, UploadImage uploadImage) {
        this.title = title;
        this.content = content;
        this.image.updateImageEntity(uploadImage);
    }
}
