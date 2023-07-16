package com.eleven.miniproject.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Embedded
    private UploadImage uploadImage;

    public Image(UploadImage uploadImage) {
        this.uploadImage = uploadImage;
    }
}
