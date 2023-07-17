package com.eleven.miniproject.user.controller;

import com.eleven.miniproject.user.dto.LoginRequestDto;
import com.eleven.miniproject.user.dto.SignupRequestDto;
import com.eleven.miniproject.user.dto.StatusCodeDto;
import com.eleven.miniproject.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public StatusCodeDto signup(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return new StatusCodeDto(HttpStatus.OK.value(),"회원가입 성공");
    }

    @PostMapping("/user/login")
    public StatusCodeDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        userService.login(requestDto, res);

        return new StatusCodeDto(HttpStatus.OK.value(),"로그인 성공");
    }
}