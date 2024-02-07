package com.alugai.alugaai.service;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.dto.UserRegistrationDto;

import java.util.Optional;

public interface UserService {

    void saveUser(UserRegistrationDto registrationDto);

    void updateUser(User user);

    Optional<User> findByEmail(String email);

}
