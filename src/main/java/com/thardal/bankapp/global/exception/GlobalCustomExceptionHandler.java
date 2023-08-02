package com.thardal.bankapp.global.exception;

import com.thardal.bankapp.global.dto.RestResponse;
import com.thardal.bankapp.global.exceptions.BusinessException;
import com.thardal.bankapp.global.exceptions.ItemNotFoundException;
import com.thardal.bankapp.kafka.dto.KafkaMessage;
import com.thardal.bankapp.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalCustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();
        RestResponse<String> restResponse = RestResponse.error(errorMessage);
        restResponse.setMessage(errorMessage);

        logError(errorMessage);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        String errorMessage = "An business error occurred: " + ex.getBaseErrorMessages().getMessage();
        RestResponse<String> restResponse = RestResponse.error(errorMessage);
        restResponse.setMessage(errorMessage);

        logError(errorMessage);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Object> handleCustomException(ItemNotFoundException ex) {
        String errorMessage = "An error occurred: " + ex.getBaseErrorMessages().getMessage();

        RestResponse<String> restResponse = RestResponse.error(errorMessage);
        restResponse.setMessage(errorMessage);

        logError(errorMessage);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    private void logError(String errorMessage) {
        KafkaMessage logMessage = KafkaMessage.builder()
                .message(errorMessage)
                .dateTime(new Date())
                .build();

        logService.log(logMessage);
    }
}


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