package com.Myapps.Twitter.Exceptions;

import com.Myapps.Twitter.Exceptions.AuthExceptions.EmailAlreadyTakenException;
import com.Myapps.Twitter.Exceptions.AuthExceptions.InvalidVerificationCodeException;
import com.Myapps.Twitter.Exceptions.AuthExceptions.NoSuchUserException;
import com.Myapps.Twitter.Models.Response.ApiResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<ApiResponse> EmailAlreadyTakenExceptionHandler(EmailAlreadyTakenException exception){
        return  ResponseEntity.ok().body(ApiResponse.failure(exception.getMessage()));
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ApiResponse> EmailAlreadyTakenExceptionHandler(NoSuchUserException exception){
        return  ResponseEntity.ok().body(ApiResponse.failure(exception.getMessage()));
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<ApiResponse> InvalidVerificationCodeExceptionHandler(InvalidVerificationCodeException exception){
        return  ResponseEntity.ok().body(ApiResponse.failure(exception.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> ExceptionHandler(Exception exception){
        return  ResponseEntity.ok().body(ApiResponse.failure(exception.getMessage()));
    }
}
