package com.edem.medlink.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(VerificationFailedException.class)
    public ResponseEntity<ErrorResponse> handleVerificationFailedException(VerificationFailedException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .detail("Product not found")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .detail("Product not found")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


}
