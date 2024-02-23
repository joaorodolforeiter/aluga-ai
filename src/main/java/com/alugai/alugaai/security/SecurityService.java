package com.alugai.alugaai.security;

import com.alugai.alugaai.model.User;
import com.alugai.alugaai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;

    public User getSessionUser() {

        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("User not logged in");
        }

        return userService.findByEmail(authentication.getName()).get();

    }

}