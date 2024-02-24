package com.alugai.alugaai.controller;

import com.alugai.alugaai.dto.RentRegistrationDto;
import com.alugai.alugaai.security.CurrentUserProvider;
import com.alugai.alugaai.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RentController {

    private final CurrentUserProvider currentUserProvider;
    private final RentService rentService;

    @GetMapping("/rents")
    public String getRentPage(Model model) {

        var loggedUser = currentUserProvider.getSessionUser();
        var rents = rentService.findByRenter(loggedUser);

        model.addAttribute("rents", rents);

        return "rents";

    }

    @GetMapping("/rents/pending")
    public String getPendingRentPage(Model model) {

        var loggedUser = currentUserProvider.getSessionUser();
        var rents = rentService.findByProductOwner(loggedUser);

        model.addAttribute("rents", rents);

        return "pending-rents";

    }

    @PostMapping("/rent/{id}")
    public String addRent(
            @ModelAttribute("rent") RentRegistrationDto rent,
            @PathVariable Long id
    ) {

        rent.setProductId(id);

        var savedRent = rentService.create(rent);

        if (savedRent.isEmpty()) {
            return "redirect:/";
        }

        return "redirect:/rents";

    }

    @PostMapping("/rents/pending/accept/{id}")
    public String acceptRent(@PathVariable Long id) {

        rentService.acceptRent(id);
        return "redirect:/rents/pending";

    }

}
