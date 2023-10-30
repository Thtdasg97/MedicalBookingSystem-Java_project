package com.example.asm03spring.service;
import com.example.asm03spring.dto.AuthenticationRequest;
import com.example.asm03spring.dto.AuthenticationResponse;
import com.example.asm03spring.dto.RegisterRequest;
import com.example.asm03spring.entity.Patient;
import com.example.asm03spring.exception.UserNotFoundException;
import com.example.asm03spring.service.JwtService;
import com.example.asm03spring.dao.RoleRepository;
import com.example.asm03spring.dao.UserRepository;
import com.example.asm03spring.entity.Role;
import com.example.asm03spring.entity.User;
import com.example.asm03spring.exception.RegistrationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    // method use to register user
    public AuthenticationResponse register(RegisterRequest request) throws RegistrationException {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RegistrationException("Password do not match");
        }

        Role role = roleRepository.findRoleById(4);

        if(request.getName() == null || request.getName().equals("")) {
            throw new RegistrationException("Name is required.");

        }

        if(request.getEmail().equals("") || !request.getEmail().contains("@")) {
            throw new RegistrationException("Email is required and need correct format.");

        }

        var user = User.builder()
                .name(request.getName())
                .gender(request.getGender())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                // n
                .historicalMedicine(request.getHistoricalMedicine())
                .role(role)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .message("Registration successful.")
                .token(jwtToken)
                .build();
    }

    // method use to authenticate user login
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                    () -> new UserNotFoundException("User not found with Email: " + request.getEmail()));
            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse
                    .builder()
                    .message("Log-in successful.")
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException exc) {
            throw new AuthenticationException("Invalid email or password!") {
            };
        }
        }

    }

