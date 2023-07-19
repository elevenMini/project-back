package com.eleven.miniproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(map);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map> nullPointExceptionHandler(NullPointerException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(map);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> uncatchedException(Exception exception) {
        Map<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(map);
    }
}
