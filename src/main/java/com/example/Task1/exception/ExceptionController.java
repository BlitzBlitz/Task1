package com.example.Task1.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleExceptions(ValidationException e){
        return new ResponseEntity<>(e.getMessage(),new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }



}
