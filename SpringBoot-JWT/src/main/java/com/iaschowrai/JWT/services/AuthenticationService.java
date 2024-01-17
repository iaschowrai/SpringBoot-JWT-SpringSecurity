package com.iaschowrai.JWT.services;


import com.iaschowrai.JWT.dto.SignUpRequest;
import com.iaschowrai.JWT.entities.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
}
