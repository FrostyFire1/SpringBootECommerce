package com.frosty.SpringBootECommerce.security.service;

import com.frosty.SpringBootECommerce.payload.APIResponse;
import com.frosty.SpringBootECommerce.security.request.LoginRequest;
import com.frosty.SpringBootECommerce.security.request.SignupRequest;
import com.frosty.SpringBootECommerce.security.response.UserDetailsResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {
  ResponseEntity<?> registerUser(@Valid SignupRequest request);

  ResponseEntity<?> authenticateUser(LoginRequest request);

  String getUsernameFromAuth(Authentication auth);

  UserDetailsResponse getUserDetailsFromAuth(Authentication auth);

  ResponseEntity<APIResponse> logoutUser();
}
