package com.iaschowrai.JWT.services;

import com.iaschowrai.JWT.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

// Interface defining JWT-related operations
public interface JWTService {

    // Method to extract the username from a JWT token
    String extractUserName(String token);

    // Method to generate a JWT token for the provided UserDetails
    String generateToken(UserDetails userDetails);

    // Method to check if a JWT token is valid for the provided UserDetails
    boolean isTokenValid(String token, UserDetails userDetails);

    // Method to generate a refresh token for the provided UserDetails with extra claims
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}


