package com.eleven.miniproject.board.getBoard.service;


import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.entity.Board;
import com.eleven.miniproject.board.repository.BoardRepository;
import com.eleven.miniproject.user.entity.User;
import com.eleven.miniproject.user.jwt.JwtUtil;
import com.eleven.miniproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardsGetService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public List<BoardResponseDto> getBoardsInUser(HttpServletRequest request) {
        String username = findUsernameInJwtToken(request);
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        List<Board> boardList = boardRepository.findByUser_Username(findUser.getUsername());
        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    public List<BoardResponseDto> getBoards() {

        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto getSelectedBoard(Long boarId, HttpServletRequest request) {

        Board findBoard = boardRepository.findById(boarId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        return new BoardResponseDto(findBoard);

    }

    public List<BoardResponseDto> getBoardsTop5Recently() {
        List<Board> top5Boards = boardRepository.findTop5ByOrderByModifiedAtDesc();
        return top5Boards.stream().map(BoardResponseDto::new).toList();
    }

    public List<BoardResponseDto> getBoardsTop5InUserRecently(HttpServletRequest request) {
        String username = findUsernameInJwtToken(request);

        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다"));

        List<Board> top5BoardsInUser = boardRepository.findTop5ByUser_UsernameOrderByModifiedAtDesc(findUser.getUsername());

        return top5BoardsInUser.stream().map(BoardResponseDto::new).toList();
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
