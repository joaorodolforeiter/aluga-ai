package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final SecurityService securityService;

    @GetMapping("/account")
    public String getAccountPage(Model model) {

        Optional<User> optionalLoggedUser = securityService.getSessionUser();

        if (optionalLoggedUser.isEmpty()) {
            return "redirect:/login";
        }

        optionalLoggedUser.ifPresent(user -> model.addAttribute("user", user));

        return "account";

    }

}
