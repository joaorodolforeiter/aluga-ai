package com.alugai.alugaai.service;

import com.alugai.alugaai.model.Role;
import com.alugai.alugaai.model.User;
import com.alugai.alugaai.dto.UserRegistrationDto;
import com.alugai.alugaai.repository.RoleRepository;
import com.alugai.alugaai.repository.UserRepository;
import com.alugai.alugaai.security.CurrentUserProvider;
import com.alugai.alugaai.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CurrentUserProvider currentUserProvider;
    private final StorageService storageService;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> register(UserRegistrationDto userDto) {

        Optional<User> existingUserEmail = userRepository.findByEmail(userDto.getEmail());

        if (existingUserEmail.isPresent()) {
            return Optional.empty();
        }

        Role role = roleRepository.findByName("USER");

        User user = User
                .builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singletonList(role))
                .build();

        return Optional.of(userRepository.save(user));

    }

    public User updateProfilePhoto(MultipartFile newPhoto) {

        var loggedUser = currentUserProvider.getSessionUser();

        var fileId = storageService.store(newPhoto);
        loggedUser.setPhotoPath(fileId);

        return userRepository.save(loggedUser);

    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
