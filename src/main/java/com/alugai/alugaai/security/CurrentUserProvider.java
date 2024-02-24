package com.alugai.alugaai.security;

import com.alugai.alugaai.model.User;
import com.alugai.alugaai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final UserRepository userRepository;

    public User getSessionUser() {

        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("User not logged in");
        }

        return userRepository.findByEmail(authentication.getName()).get();

    }

}