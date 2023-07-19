package com.eleven.miniproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map> validException(MethodArgumentNotValidException exception) {
        Map<String, Object> map = new HashMap<>();
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        map.put("message", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(map);
    }
}
