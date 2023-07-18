package com.eleven.miniproject.board.createBoard.service;


import com.eleven.miniproject.board.repository.BoardRepository;
import com.eleven.miniproject.board.repository.ImageRepository;
import com.eleven.miniproject.board.dto.BoardRequestDto;
import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.Image;
import com.eleven.miniproject.board.entity.UploadImage;
import com.eleven.miniproject.user.entity.User;
import com.eleven.miniproject.user.jwt.JwtUtil;
import com.eleven.miniproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCreateService {


    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final JwtUtil jwtUtil;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) throws IOException {
        // 사용자 정보 확인 로직
        String username = findUsernameInJwtToken(request);
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));


        // 이미지 저장
        UploadImage uploadImage = s3Service.upload(requestDto.getImage());

        Image savedImage = null;
        if (uploadImage != null) {
            savedImage = imageRepository.save(new Image(uploadImage));
        }

        // 게시글 저장
        Board savedBoard = boardRepository.save(new Board(findUser, requestDto.getTitle(), requestDto.getContent(), savedImage));

        return new BoardResponseDto(savedBoard);
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
