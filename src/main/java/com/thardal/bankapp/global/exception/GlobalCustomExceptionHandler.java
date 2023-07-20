package com.thardal.bankapp.global.exception;

import com.thardal.bankapp.global.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {
//        Date errorDate = new Date();
//        String errorMessage = ex.getMessage();
//        String description = webRequest.getDescription(false);
//
//        GlobalExceptionResponse globalExceptionResponse = new GlobalExceptionResponse(errorDate, errorMessage, description);
//
//        return new ResponseEntity<>(globalExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleCustomException(ItemNotFoundException ex) {
        String errorMessage = "An error occurred: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}