package com.frosty.SpringBootECommerce.security.service;

import com.frosty.SpringBootECommerce.exception.APIException;
import com.frosty.SpringBootECommerce.model.AppRole;
import com.frosty.SpringBootECommerce.model.Role;
import com.frosty.SpringBootECommerce.model.User;
import com.frosty.SpringBootECommerce.repository.RoleRepository;
import com.frosty.SpringBootECommerce.repository.UserRepository;
import com.frosty.SpringBootECommerce.security.jwt.JwtUtils;
import com.frosty.SpringBootECommerce.security.request.LoginRequest;
import com.frosty.SpringBootECommerce.security.request.SignupRequest;
import com.frosty.SpringBootECommerce.security.response.UserDetailsResponse;
import com.frosty.SpringBootECommerce.security.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceProvider implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest request) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
        } catch (AuthenticationException e){
            Map<String,Object> map = new HashMap<>();
            map.put("message", "Bad Credentials");
            map.put("status", false);

            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsProvider user = (UserDetailsProvider) authentication.getPrincipal();
        ResponseCookie jwt = jwtUtils.generateJwtCookie(user);

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwt.toString())
                .body(new UserDetailsResponse(user.getId(), user.getUsername(), roles));

    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Username " + request.getUsername() + " is already in use"));
        }
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email " + request.getEmail() + " is already in use"));
        }
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        Set<String> strRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            roles.add(roleRepository.findByRole(AppRole.USER)
                    .orElseThrow(() -> new APIException("Couldn't find user role")));
        } else {
            strRoles.forEach(role -> {
                Role appRole;
                try{
                    appRole = roleRepository.findByRole(AppRole.valueOf(role.toUpperCase()))
                            .orElseThrow(() -> new APIException("Couldn't find role: " + role));
                } catch (IllegalArgumentException e){
                    appRole = roleRepository.findByRole(AppRole.USER)
                            .orElseThrow(() -> new APIException("Couldn't find user role"));
                }
                roles.add(appRole);
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @Override
    public String getUsernameFromAuth(Authentication auth) {
        if (auth == null) throw new APIException("You're not authenticated!");
        return auth.getName();
    }

    @Override
    public UserDetailsResponse getUserDetailsFromAuth(Authentication auth) {
        UserDetailsProvider user = (UserDetailsProvider) auth.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return new UserDetailsResponse(user.getId(), user.getUsername(), roles);
    }
}
