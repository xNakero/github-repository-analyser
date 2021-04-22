package com.example.githubrepositoryanalyser.exceptions;

import com.example.githubrepositoryanalyser.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> userNotFoundHandler(UserNotFoundException e) {
        ExceptionResponseDto responseDto = new ExceptionResponseDto(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}
