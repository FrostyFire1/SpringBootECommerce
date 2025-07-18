package com.frosty.SpringBootECommerce.service;

import com.frosty.SpringBootECommerce.security.request.LoginRequest;
import com.frosty.SpringBootECommerce.security.request.SignupRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerUser(@Valid SignupRequest request);

    ResponseEntity<?> authenticateUser(LoginRequest request);
}
