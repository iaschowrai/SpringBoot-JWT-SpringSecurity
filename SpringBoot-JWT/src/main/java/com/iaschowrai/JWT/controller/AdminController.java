package com.iaschowrai.JWT.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Spring annotation to declare this class as a REST controller
@RestController
// Define the base request mapping for the controller
@RequestMapping("/api/v1/admin")
// Lombok annotation to generate a constructor with required fields
@RequiredArgsConstructor
public class AdminController {

    // Define a GET mapping for the endpoint
    @GetMapping
    // Method to handle the GET request and return a ResponseEntity with a String response
    public ResponseEntity<String> sayHello() {
        // Return an OK response with the message "Hi, Admin"
        return ResponseEntity.ok("Hi, Admin");
    }
}

