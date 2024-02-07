package com.alugai.alugaai.service.impl;

import com.alugai.alugaai.domain.Role;
import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.dto.UserRegistrationDto;
import com.alugai.alugaai.repository.RoleRepository;
import com.alugai.alugaai.repository.UserRepository;
import com.alugai.alugaai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserRegistrationDto registrationDto) {

        User user = new User();

        user.setEmail(registrationDto.getEmail());
        user.setName(registrationDto.getName());
        user.setSurname(registrationDto.getSurname());

        user.setPassword(
                passwordEncoder.encode(registrationDto.getPassword())
        );

        Role role = roleRepository.findByName("USER");
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
