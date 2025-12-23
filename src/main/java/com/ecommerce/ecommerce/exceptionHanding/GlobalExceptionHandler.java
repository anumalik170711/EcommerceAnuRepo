package com.ecommerce.ecommerce.exceptionHanding;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<String> handleUserNameNotFound(UserNameNotFound ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
