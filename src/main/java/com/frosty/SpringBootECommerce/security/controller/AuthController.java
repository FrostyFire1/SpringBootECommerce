package com.frosty.SpringBootECommerce.security.controller;

import com.frosty.SpringBootECommerce.payload.APIResponse;
import com.frosty.SpringBootECommerce.security.request.LoginRequest;
import com.frosty.SpringBootECommerce.security.request.SignupRequest;
import com.frosty.SpringBootECommerce.security.response.UserDetailsResponse;
import com.frosty.SpringBootECommerce.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest request) {
        return authService.authenticateUser(request);
    }

    @PostMapping("/signout")
    public ResponseEntity<APIResponse> logoutUser() {
        return authService.logoutUser();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest request) {
        return authService.registerUser(request);
    }

    @GetMapping("/username")
    public ResponseEntity<APIResponse> getUsernameFromAuth(Authentication auth) {
        return ResponseEntity.ok(new APIResponse(authService.getUsernameFromAuth(auth), true));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetailsResponse> getUserDetailsFromAuth(Authentication auth) {
        return ResponseEntity.ok(authService.getUserDetailsFromAuth(auth));
    }
}
