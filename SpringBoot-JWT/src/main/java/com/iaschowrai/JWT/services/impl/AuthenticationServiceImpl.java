package com.iaschowrai.JWT.services.impl;

import com.iaschowrai.JWT.dto.JwtAuthenticationResponse;
import com.iaschowrai.JWT.dto.RefreshTokenRequest;
import com.iaschowrai.JWT.dto.SignInRequest;
import com.iaschowrai.JWT.dto.SignUpRequest;
import com.iaschowrai.JWT.entities.Role;
import com.iaschowrai.JWT.entities.User;
import com.iaschowrai.JWT.repository.UserRepository;
import com.iaschowrai.JWT.services.AuthenticationService;
import com.iaschowrai.JWT.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
// Spring annotation to declare this class as a service component
@Service
// Lombok annotation to generate a constructor with required fields
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    // Injected UserRepository for accessing user data in the database
    private final UserRepository userRepository;

    // Injected PasswordEncoder for encoding and decoding passwords
    private final PasswordEncoder passwordEncoder;

    // Injected AuthenticationManager for authenticating users
    private final AuthenticationManager authenticationManager;

    // Injected JWTService for handling JWT-related operations
    private final JWTService jwtService;

    // Method to handle user signup
    public User signup(SignUpRequest signUpRequest) {
        // Create a new User instance
        User user = new User();

        // Set user details from the signup request
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setRole(Role.USER);
        // Encode and set the user's password
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Save the user to the database
        return userRepository.save(user);
    }

    // Method to handle user signin and generate JWT tokens
    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        try {
            // Authenticate the user using the provided email and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

            // Retrieve the user from the database using the email
            var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalAccessException("Invalid email or password"));

            // Generate JWT and refresh tokens
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            // Create a JwtAuthenticationResponse with the generated tokens
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);

            return jwtAuthenticationResponse;
        } catch (IllegalAccessException e) {
            // Handle authentication failure by throwing a RuntimeException (can be logged or handled differently)
            throw new RuntimeException("Error during authentication", e);
        }
    }

    // Method to handle token refresh and generate a new JWT token
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        // Extract the user email from the refresh token
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());

        // Retrieve the user from the database using the email
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        // Check if the refresh token is valid
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            // Generate a new JWT token
            var jwt = jwtService.generateToken(user);

            // Create a JwtAuthenticationResponse with the new JWT token and the original refresh token
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }
}

