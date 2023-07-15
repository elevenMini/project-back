package com.eleven.miniproject;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "CICD 작동 테스트 : 바꾼결과 잘나올까? 두번째 테스트22";
    }
}
