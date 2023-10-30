package com.example.asm03spring.exception;

import com.example.asm03spring.dto.UserErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserRestExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<UserErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        userErrorResponse.setMessage("Email already in use.");
        userErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handlingException (Exception exc) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        userErrorResponse.setMessage(exc.getMessage());
        userErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(userErrorResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<UserErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        userErrorResponse.setMessage("Access denied. You do not have permission to access this resource.");
        userErrorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(userErrorResponse, HttpStatus.FORBIDDEN);
    }
}
