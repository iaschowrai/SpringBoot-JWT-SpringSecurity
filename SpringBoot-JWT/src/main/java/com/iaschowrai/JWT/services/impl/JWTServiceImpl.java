package com.iaschowrai.JWT.services.impl;


import com.iaschowrai.JWT.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
// Spring annotation to declare this class as a service component
@Service
public class JWTServiceImpl implements JWTService {

    // Method to generate a JWT token for the provided UserDetails
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Token valid for 24 hours
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign the token with the specified algorithm
                .compact();
    }

    // Method to generate a refresh token for the provided UserDetails with extra claims
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // Refresh token valid for 7 days
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Method to extract all claims from a JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // Method to get the signing key used for JWT verification
    private Key getSignKey() {
        // Decode the base64-encoded key and create an HMAC SHA key
        byte[] key = Decoders.BASE64.decode("T0wFc33lLZsYXKy6QdiXPeVqgkCP9S0QsDoT+5Yyqhk=");
        return Keys.hmacShaKeyFor(key);
    }

    // Method to extract the username from a JWT token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic method to extract a specific claim using a provided function
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Extract all claims from the token
        final Claims claims = extractAllClaims(token);
        // Apply the provided function to get the desired claim
        return claimsResolver.apply(claims);
    }

    // Method to check if a JWT token is valid for the provided UserDetails
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // Extract the username from the token
        final String username = extractUserName(token);
        // Check if the extracted username matches the username from UserDetails and the token is not expired
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Method to check if a JWT token is expired
    private boolean isTokenExpired(String token) {
        // Compare the expiration time of the token with the current date
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

