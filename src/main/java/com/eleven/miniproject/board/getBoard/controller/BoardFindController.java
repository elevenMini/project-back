package com.eleven.miniproject.board.getBoard.controller;


import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.getBoard.service.BoardsGetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardFindController {

    private final BoardsGetService boardsGetService;

    @GetMapping("/boards/user")
    public List<BoardResponseDto> getBoardsInUser(HttpServletRequest request) {
        return boardsGetService.getBoardsInUser(request);
    }

    @GetMapping("/boards")
    public List<BoardResponseDto> getBoards() {
        return boardsGetService.getBoards();
    }

    @GetMapping("/boards/{boardId}")
    public BoardResponseDto getSelectedBoard(@PathVariable("boardId") Long boarId, HttpServletRequest request) {
        return boardsGetService.getSelectedBoard(boarId, request);
    }

    //1. 전체에서 탑 5
    @GetMapping("/boards/top5")
    public List<BoardResponseDto> getBoardsTop5Recently() {
        return boardsGetService.getBoardsTop5Recently();
    }

    @GetMapping("/boards/user/top5")
    public List<BoardResponseDto> getBoardsTop5InUserRecently(HttpServletRequest request) {
        return boardsGetService.getBoardsTop5InUserRecently(request);
    }
}
