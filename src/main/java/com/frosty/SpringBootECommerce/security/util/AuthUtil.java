package com.frosty.SpringBootECommerce.security.util;

import com.frosty.SpringBootECommerce.model.User;
import com.frosty.SpringBootECommerce.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    private UserRepository userRepository;

    public User getPrincipal() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public String getPrincipalEmail() {
        return getPrincipal().getEmail();
    }



    public Long getPrincipalId(){
        return getPrincipal().getId();
    }

}
