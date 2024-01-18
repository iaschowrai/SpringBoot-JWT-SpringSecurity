package com.iaschowrai.JWT.services;


import com.iaschowrai.JWT.dto.JwtAuthenticationResponse;
import com.iaschowrai.JWT.dto.SignInRequest;
import com.iaschowrai.JWT.dto.SignUpRequest;
import com.iaschowrai.JWT.entities.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);
}
