package com.eleven.miniproject.board.deleteBoard.controller;

import com.eleven.miniproject.board.deleteBoard.service.BoardDeleteService;
import com.eleven.miniproject.user.dto.StatusCodeDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardDeleteController {

    private final BoardDeleteService boardDeleteService;

    @DeleteMapping("/boards/{boardId}")
    public StatusCodeDto deleteBoard(@PathVariable("boardId") Long boardId, HttpServletRequest request) {
        return boardDeleteService.deleteBoard(boardId, request);
    }
}
