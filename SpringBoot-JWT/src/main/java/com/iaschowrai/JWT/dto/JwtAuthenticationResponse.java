package com.iaschowrai.JWT.dto;

import lombok.Data;

// Lombok annotation to generate getter, setter, equals, hashCode, and toString methods
@Data
public class JwtAuthenticationResponse {

    // Field to store the JWT token
    private String token;

    // Field to store the refresh token
    private String refreshToken;
}
