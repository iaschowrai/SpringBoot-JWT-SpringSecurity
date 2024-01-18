package com.iaschowrai.JWT.controller;


import com.iaschowrai.JWT.dto.JwtAuthenticationResponse;
import com.iaschowrai.JWT.dto.RefreshTokenRequest;
import com.iaschowrai.JWT.dto.SignInRequest;
import com.iaschowrai.JWT.dto.SignUpRequest;
import com.iaschowrai.JWT.entities.User;
import com.iaschowrai.JWT.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Spring annotation to declare this class as a REST controller
@RestController
// Define the base request mapping for the controller
@RequestMapping("/api/v1/auth")
// Lombok annotation to generate a constructor with required fields
@RequiredArgsConstructor
public class AuthenticationController {

    // Injected AuthenticationService for handling authentication-related operations
    private final AuthenticationService authenticationService;

    // Define a POST mapping for the "/signup" endpoint
    @PostMapping("/signup")
    // Method to handle the POST request for user signup
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        // Return an OK response with the user details created during signup
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    // Define a POST mapping for the "/signin" endpoint
    @PostMapping("/signin")
    // Method to handle the POST request for user signin
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        // Return an OK response with the JWT authentication response for successful signin
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    // Define a POST mapping for the "/refresh" endpoint
    @PostMapping("/refresh")
    // Method to handle the POST request for refreshing JWT tokens
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        // Return an OK response with the refreshed JWT authentication response
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}

