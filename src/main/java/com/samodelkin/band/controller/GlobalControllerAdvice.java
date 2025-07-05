package com.samodelkin.band.controller;

import com.samodelkin.band.error.ErrorMessage;
import com.samodelkin.band.error.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> onError(UserNotFoundException error) {
        return ResponseEntity
                .status(400)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessage.builder()
                        .message(error.getMessage())
                        .build());
    }

}
