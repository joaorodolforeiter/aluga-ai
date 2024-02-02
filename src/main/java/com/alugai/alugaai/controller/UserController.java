package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.security.SecurityService;
import com.alugai.alugaai.service.UserService;
import com.alugai.alugaai.storage.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final SecurityService securityService;
    private final StorageService storageService;
    private final UserService userService;

    @GetMapping("/account")
    public String getAccountPage(Model model) {

        Optional<User> optionalLoggedUser = securityService.getSessionUser();

        if (optionalLoggedUser.isEmpty()) {
            return "redirect:/login";
        }

        optionalLoggedUser.ifPresent(user -> model.addAttribute("user", user));

        return "account";

    }

    @GetMapping("/public/{email}.png")
    public ResponseEntity<?> getUserProfileImage(Model model, @PathVariable String email) {

        var optionalUser = userService.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Resource file = storageService.loadAsResource(optionalUser.get().getPhotoPath());

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

    }

    @PostMapping("/add/update-photo")
    public String addProfilePhoto(@RequestPart("photo") MultipartFile photo) {

        Optional<User> optionalLoggedUser = securityService.getSessionUser();

        if (optionalLoggedUser.isEmpty()) {
            return "redirect:/login";
        }

        storageService.store(photo);

        User user = optionalLoggedUser.get();
        user.setPhotoPath(photo.getOriginalFilename());

        userService.updateUser(user);

        return "redirect:/account";

    }

}
