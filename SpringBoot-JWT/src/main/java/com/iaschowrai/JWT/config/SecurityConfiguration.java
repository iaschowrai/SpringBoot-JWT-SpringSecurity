package com.iaschowrai.JWT.config;

import com.iaschowrai.JWT.entities.Role;
import com.iaschowrai.JWT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuration class for defining security settings using Spring Security
@Configuration
// Enable Spring Security for the application
@EnableWebSecurity
// Lombok annotation to generate a constructor with required fields
@RequiredArgsConstructor
public class SecurityConfiguration {

    // Injected JWTAuthenticationFilter for processing JWT authentication
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    // Injected UserService for retrieving user details
    private final UserService userService;

    // Define a SecurityFilterChain for handling security configurations
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection
                .csrf(AbstractHttpConfigurer::disable)
                // Define authorization rules for different endpoints
                .authorizeHttpRequests(request ->
                        request
                                // Allow access to authentication-related endpoints without authentication
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                // Require ADMIN authority for /api/v1/admin endpoint
                                .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
                                // Require USER authority for /api/v1/user endpoint
                                .requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())
                                // Authenticate any other request
                                .anyRequest().authenticated()
                )
                // Set session management to STATELESS, as JWT tokens are used (no server-side session)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set the custom JWTAuthenticationFilter before the UsernamePasswordAuthenticationFilter in the filter chain
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Return the configured HttpSecurity object
        return http.build();
    }

    // Define an AuthenticationProvider bean for customizing authentication logic
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // Set the userDetailsService to retrieve user details
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        // Set the password encoder for comparing passwords
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // Return the configured AuthenticationProvider
        return authenticationProvider;
    }

    // Define a PasswordEncoder bean for encrypting and comparing passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define an AuthenticationManager bean using AuthenticationConfiguration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Return the configured AuthenticationManager
        return config.getAuthenticationManager();
    }
}
