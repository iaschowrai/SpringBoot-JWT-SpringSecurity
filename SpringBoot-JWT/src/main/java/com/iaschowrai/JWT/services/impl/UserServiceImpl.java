package com.iaschowrai.JWT.services.impl;

import com.iaschowrai.JWT.repository.UserRepository;
import com.iaschowrai.JWT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Spring annotation to declare this class as a service component
@Service
// Lombok annotation to generate a constructor with required fields
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Injected UserRepository for accessing user data in the database
    private final UserRepository userRepository;

    // Implementation of the UserDetailsService interface
    @Override
    public UserDetailsService userDetailsService() {
        // Return an instance of UserDetailsService as an anonymous inner class
        return new UserDetailsService() {

            // Implementation of the loadUserByUsername method to load UserDetails by username
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Find a user by email in the userRepository or throw an exception if not found
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
            }
        };
    }
}
