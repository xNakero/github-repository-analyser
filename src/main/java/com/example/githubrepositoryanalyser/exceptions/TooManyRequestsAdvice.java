package com.example.githubrepositoryanalyser.exceptions;

import com.example.githubrepositoryanalyser.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TooManyRequestsAdvice {

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ExceptionResponseDto> tooManyRequestsHandler(TooManyRequestsException e) {
        return new ResponseEntity<>(new ExceptionResponseDto(e.getMessage()), HttpStatus.TOO_MANY_REQUESTS);
    }
}
