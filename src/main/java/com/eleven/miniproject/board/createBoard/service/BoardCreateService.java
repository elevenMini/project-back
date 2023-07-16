package com.eleven.miniproject.board.createBoard.service;


import com.eleven.miniproject.board.createBoard.repository.BoardCreateRepository;
import com.eleven.miniproject.board.createBoard.repository.ImageRepository;
import com.eleven.miniproject.board.dto.BoardRequestDto;
import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.Image;
import com.eleven.miniproject.board.entity.UploadImage;
import com.eleven.miniproject.board.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardCreateService {

    @Value("${file.dir}")
    private String fileDir;

    private final BoardCreateRepository boardCreateRepository;
    private final ImageRepository imageRepository;
    private final FileUtil fileUtil;
    private final S3Service s3Service;

    public BoardResponseDto createBoard(BoardRequestDto requestDto) throws IOException {
        // 이미지 저장
//        UploadImage uploadImage = fileUtil.storeImage(requestDto.getImage());
        UploadImage uploadImage = s3Service.upload(requestDto.getImage());

        Image savedImage = imageRepository.save(new Image(uploadImage));

        // 게시글 저장
        Board savedBoard = boardCreateRepository.save(new Board(requestDto.getTitle(), requestDto.getContent(), savedImage));

        return new BoardResponseDto(savedBoard, fileDir);
    }
}
