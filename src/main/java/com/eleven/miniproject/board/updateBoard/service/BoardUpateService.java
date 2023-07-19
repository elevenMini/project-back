package com.eleven.miniproject.board.updateBoard.service;


import com.eleven.miniproject.board.createBoard.service.S3Service;
import com.eleven.miniproject.board.dto.BoardRequestDto;
import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.Image;
import com.eleven.miniproject.board.entity.UploadImage;
import com.eleven.miniproject.board.repository.BoardRepository;
import com.eleven.miniproject.board.repository.ImageRepository;
import com.eleven.miniproject.user.entity.User;
import com.eleven.miniproject.user.jwt.JwtUtil;
import com.eleven.miniproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardUpateService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final JwtUtil jwtUtil;
    private final S3Service s3Service;


    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, HttpServletRequest request) throws IOException {
        String username = findUsernameInJwtToken(request);
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Board updatableBoard = boardRepository.findBoardAndImageByIdAndUsername(boardId, findUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("수정 권한이 없습니다."));

        UploadImage uploadImage;
        Image newImage = null;
        // 처음부터 없었다면,
        if (updatableBoard.getImage() == null || requestDto.getImage() == null) {
            uploadImage = s3Service.upload(requestDto.getImage());

            if (requestDto.getImage() != null) {
                newImage = imageRepository.save(new Image(uploadImage));
            }

            updatableBoard.updateBoard(requestDto.getTitle(), requestDto.getContent(), newImage);
        } else {
            String currentStoredImageName = updatableBoard.getImage().getUploadImage().getStoreFileName();

            uploadImage = s3Service.updateImage(currentStoredImageName, requestDto.getImage());
            updatableBoard.updateBoard(requestDto.getTitle(), requestDto.getContent(), uploadImage);
        }

        boardRepository.flush();

        return new BoardResponseDto(updatableBoard);
    }

    private String findUsernameInJwtToken(HttpServletRequest request) {
        String tokenFromRequest = jwtUtil.getTokenFromRequest(request);
        String tokenValue = jwtUtil.substringToken(tokenFromRequest);
        if (!jwtUtil.validateToken(tokenValue)) {
            throw new IllegalArgumentException("토큰 정보가 유효하지 않습니다");
        }
        return jwtUtil.getUserInfoFromToken(tokenValue).getSubject();
    }
}
