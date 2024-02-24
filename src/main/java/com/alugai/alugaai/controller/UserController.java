package com.alugai.alugaai.controller;

import com.alugai.alugaai.security.CurrentUserProvider;
import com.alugai.alugaai.service.UserService;
import com.alugai.alugaai.storage.StorageService;
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

@Controller
@RequiredArgsConstructor
public class UserController {

    private final CurrentUserProvider currentUserProvider;
    private final StorageService storageService;
    private final UserService userService;

    @GetMapping("/account")
    public String getAccountPage(Model model) {

        var loggedUser = currentUserProvider.getSessionUser();
        model.addAttribute("user", loggedUser);

        return "account";

    }

    @GetMapping("/public/{email}.png")
    public ResponseEntity<?> getUserProfileImage(@PathVariable String email) {

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

        userService.updateProfilePhoto(photo);
        return "redirect:/account";

    }

}
