package com.eleven.miniproject.board.deleteBoard.service;


import com.eleven.miniproject.board.createBoard.service.S3Service;
import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.entity.Image;
import com.eleven.miniproject.board.repository.BoardRepository;
import com.eleven.miniproject.board.repository.ImageRepository;
import com.eleven.miniproject.user.dto.StatusCodeDto;
import com.eleven.miniproject.user.entity.User;
import com.eleven.miniproject.user.jwt.JwtUtil;
import com.eleven.miniproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDeleteService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final S3Service s3Service;
    private final JwtUtil jwtUtil;

    public StatusCodeDto deleteBoard(Long boardId, HttpServletRequest request) {
        int statusCode;
        String responseMessage;

        String username = findUsernameInJwtToken(request);

        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        Board boardToDelete = boardRepository.findBoardAndImageByIdAndUsername(boardId, findUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("삭제 권한이 없습니다."));

        try {
            Image image = boardToDelete.getImage();
            if (image != null) {
                s3Service.deleteImage(image.getUploadImage().getStoreFileName());
                imageRepository.delete(image);
            }
            boardRepository.delete(boardToDelete);

            statusCode = HttpStatus.OK.value();
            responseMessage = "삭제 성공";

        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "삭제 실패";
        }

        return new StatusCodeDto(statusCode, responseMessage);
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
