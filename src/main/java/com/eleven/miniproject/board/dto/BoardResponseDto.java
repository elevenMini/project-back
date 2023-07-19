package com.eleven.miniproject.board.dto;


import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.UploadImage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
  
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
  
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
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
