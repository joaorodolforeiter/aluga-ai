package com.alugai.alugaai.controller;

import com.alugai.alugaai.model.User;
import com.alugai.alugaai.dto.UserRegistrationDto;
import com.alugai.alugaai.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserRegistrationDto user
    ) {

        Optional<User> existingUserEmail = userService.findByEmail(user.getEmail());

        if (existingUserEmail.isPresent()) {
            return "redirect:/register?fail";
        }

        userService.register(user);

        return "redirect:/login";

    }

}
