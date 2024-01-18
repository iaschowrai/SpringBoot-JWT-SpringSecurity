package com.iaschowrai.JWT.services;
import com.iaschowrai.JWT.dto.JwtAuthenticationResponse;
import com.iaschowrai.JWT.dto.RefreshTokenRequest;
import com.iaschowrai.JWT.dto.SignInRequest;
import com.iaschowrai.JWT.dto.SignUpRequest;
import com.iaschowrai.JWT.entities.User;

// Interface defining authentication-related operations
public interface AuthenticationService {

    // Method to handle user signup and return the created user
    User signup(SignUpRequest signUpRequest);

    // Method to handle user signin and return a JwtAuthenticationResponse with tokens
    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    // Method to handle token refresh and return a JwtAuthenticationResponse with a new JWT token
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
