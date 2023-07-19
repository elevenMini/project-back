package com.eleven.miniproject.board.updateBoard.controller;


import com.eleven.miniproject.board.dto.BoardRequestDto;
import com.eleven.miniproject.board.dto.BoardResponseDto;
import com.eleven.miniproject.board.updateBoard.service.BoardUpateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardUpateController {

    private final BoardUpateService boardUpateService;

    @PutMapping("/boards/{boardId}")
    public BoardResponseDto updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image,
            HttpServletRequest request) throws IOException {

        BoardRequestDto boardRequestDto = new BoardRequestDto(title, content, image);
        return boardUpateService.updateBoard(boardId, boardRequestDto, request);
    }
}
