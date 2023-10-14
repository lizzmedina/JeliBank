package com.example.jeliBankBackend.exceptions;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(String.valueOf(GlobalExceptionHandler.class));

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptioni (ResourseNotFoundException exception){
        logger.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
