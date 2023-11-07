package com.example.medicalbookingsystem.rest;

import com.example.medicalbookingsystem.service.JwtService;
import com.example.medicalbookingsystem.dao.UserRepository;
import com.example.medicalbookingsystem.dto.*;
import com.example.medicalbookingsystem.service.PasswordService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password-request")
    public ResponseEntity<ForgottenPasswordResponse> resetPassword (
            @RequestBody ForgottenPasswordRequest request
    ) {
        return ResponseEntity.ok(passwordService.forgottenPasswordResponse(request));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ConfirmResetPasswordResponse> confirmResetPassword(
            @RequestBody ConfirmResetPasswordRequest request
    ) {
        return ResponseEntity.ok(passwordService.confirmResetPasswordResponse(request));
    }

    @ExceptionHandler
    public ResponseEntity<ForgottenPasswordErrorResponse> handleException(NoSuchElementException exc) {
        ForgottenPasswordErrorResponse errorResponse = new ForgottenPasswordErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ConfirmResetPasswordErrorResponse> handleException(Exception exc) {
        ConfirmResetPasswordErrorResponse errorResponse = new ConfirmResetPasswordErrorResponse();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exc instanceof NoSuchElementException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exc instanceof JwtException || exc instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        }
        errorResponse.setStatus(status.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, status);
    }



}
