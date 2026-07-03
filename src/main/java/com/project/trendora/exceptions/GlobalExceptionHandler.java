package com.project.trendora.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExists ex){
        log.warn("User already exists: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ProductAlreadyExist.class)
    public ResponseEntity<?> handleProductAlreadyExists(ProductAlreadyExist ex){
        log.warn("Product already exist: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public  ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex){
        log.warn("Product not found {}",ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFound obj){
        log.warn("user not found {}",obj.getMessage());
        return ResponseEntity.badRequest().body(obj.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleGlobalRuntimeException(RuntimeException ex){
        log.error("Unexpected exception occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

