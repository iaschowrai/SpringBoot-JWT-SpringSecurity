package com.iaschowrai.JWT.config;

import com.iaschowrai.JWT.services.JWTService;
import com.iaschowrai.JWT.services.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// Component annotation to declare this class as a Spring bean/component
@Component
// Lombok annotation to generate a constructor with required fields
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    // Injected JWTService for handling JWT-related operations
    private final JWTService jwtService;

    // Injected UserService for retrieving user details
    private final UserService userService;

    // Override the doFilterInternal method from OncePerRequestFilter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract the "Authorization" header from the HTTP request
        final String authHeader = request.getHeader("Authorization");
        // Variables to store the JWT and user email
        final String jwt;
        final String userEmail;

        // Check if the Authorization header is empty or doesn't start with "Bearer "
        if (StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")) {
            // If conditions are not met, continue to the next filter in the chain
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT from the Authorization header
        jwt = authHeader.substring(7);
        // Extract the user email from the JWT
        userEmail = jwtService.extractUserName(jwt);

        // Check if the user email is not empty and no authentication is currently in the SecurityContextHolder
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the userDetailsService using the extracted user email
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            // Check if the JWT is valid for the loaded user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create a new empty SecurityContext
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Create a UsernamePasswordAuthenticationToken with user details, null credentials, and authorities
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // Set details for the authentication token using WebAuthenticationDetailsSource
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the security context
                securityContext.setAuthentication(token);
                // Set the security context in the SecurityContextHolder
                SecurityContextHolder.setContext(securityContext);
            }
        }

        // Continue to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
