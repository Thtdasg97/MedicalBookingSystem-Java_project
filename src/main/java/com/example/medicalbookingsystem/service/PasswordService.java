package com.example.medicalbookingsystem.service;
import com.example.medicalbookingsystem.dao.UserRepository;
import com.example.medicalbookingsystem.entity.User;
import com.example.medicalbookingsystem.dto.ConfirmResetPasswordRequest;
import com.example.medicalbookingsystem.dto.ConfirmResetPasswordResponse;
import com.example.medicalbookingsystem.dto.ForgottenPasswordRequest;
import com.example.medicalbookingsystem.dto.ForgottenPasswordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private Map<String, String> passwordResetRequests = new HashMap<>();

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ForgottenPasswordResponse forgottenPasswordResponse(ForgottenPasswordRequest request) {
        try {
            // find user by email
            var theUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
            // generate token and serial (UUID)
            var jwtToken = jwtService.generateToken(theUser);
            String serial = UUID.randomUUID().toString();

            passwordResetRequests.put(serial, request.getEmail());
            // return response
            return ForgottenPasswordResponse
                    .builder()
                    .message("A password reset link has been sent to your email. Please check your inbox and follow the instructions.")
                    .serial(serial)
                    .build();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Email is not exist, please check again");
        }

    }

    public ConfirmResetPasswordResponse confirmResetPasswordResponse(ConfirmResetPasswordRequest request) {
            // Verify token and extract email from the token
            String email = passwordResetRequests.get(request.getSerial());

            if (email == null) {
                throw new IllegalArgumentException("Invalid or expired serial.");
            }

            Optional<User> result = userRepository.findByEmail(email);

            User theUser = null;
            if (result.isPresent()) {
                theUser = result.get();
                if (!request.getNewPassword().equals(request.getRePassword())) {
                    throw new IllegalArgumentException("New password and re-entered password do not match.");
                }
                theUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(theUser);
            }

        var jwtToken = jwtService.generateToken(theUser);
        return ConfirmResetPasswordResponse.builder()
                .message("Reset password successful.")
                .build();
        }

    }

