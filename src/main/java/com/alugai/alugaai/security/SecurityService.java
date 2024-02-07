package com.alugai.alugaai.security;

import com.alugai.alugaai.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    public Optional<User> getSessionUser() {

        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            return Optional.of((User) authentication.getPrincipal());

        }

        return Optional.empty();

    }

}