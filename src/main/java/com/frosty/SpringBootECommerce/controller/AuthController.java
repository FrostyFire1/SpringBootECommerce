package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.security.request.LoginRequest;
import com.frosty.SpringBootECommerce.security.request.SignupRequest;
import com.frosty.SpringBootECommerce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request){
        return authService.authenticateUser(request);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest request){
        return authService.registerUser(request);
    }
}
