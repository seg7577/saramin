package com.example.saramin;

import com.example.saramin.customException.CustomExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomExceptions.BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(CustomExceptions.BadRequestException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", e.getStatusCode());
        response.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CustomExceptions.UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(CustomExceptions.UnauthorizedException e) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", e.getStatusCode());
        response.put("message", e.getMessage());
        return ResponseEntity.status(401).body(response);
    }
}
