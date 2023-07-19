package com.eleven.miniproject.board.dto;


import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.UploadImage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UploadImage uploadImage;

    public BoardResponseDto(Board savedBoard) {
        this.id = savedBoard.getId();
        this.title = savedBoard.getTitle();
        this.content = savedBoard.getContent();
        this.username = savedBoard.getUser().getUsername();
        this.createdAt = savedBoard.getCreatedAt();
        this.modifiedAt = savedBoard.getModifiedAt();

        if (savedBoard.getImage() != null) {
            this.uploadImage = savedBoard.getImage().getUploadImage();
        }
    }
}
