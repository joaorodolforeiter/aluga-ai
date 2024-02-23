package com.alugai.alugaai.service;

import com.alugai.alugaai.model.Role;
import com.alugai.alugaai.model.User;
import com.alugai.alugaai.dto.UserRegistrationDto;
import com.alugai.alugaai.repository.RoleRepository;
import com.alugai.alugaai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegistrationDto registrationDto) {

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

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
