package com.alugai.alugaai.service;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.dto.UserRegistrationDto;

import java.util.Optional;

public interface UserService {

    User saveUser(UserRegistrationDto registrationDto);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
