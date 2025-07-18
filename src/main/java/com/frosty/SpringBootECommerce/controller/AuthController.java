package com.frosty.SpringBootECommerce.controller;

import com.frosty.SpringBootECommerce.security.JwtUtils;
import com.frosty.SpringBootECommerce.security.request.LoginRequest;
import com.frosty.SpringBootECommerce.security.request.SignupRequest;
import com.frosty.SpringBootECommerce.security.response.LoginResponse;
import com.frosty.SpringBootECommerce.security.service.UserDetailsProvider;
import com.frosty.SpringBootECommerce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
