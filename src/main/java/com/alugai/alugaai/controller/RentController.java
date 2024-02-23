package com.alugai.alugaai.controller;

import com.alugai.alugaai.model.Product;
import com.alugai.alugaai.model.Rent;
import com.alugai.alugaai.repository.ProductRepository;
import com.alugai.alugaai.repository.RentRepository;
import com.alugai.alugaai.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class RentController {

    private final SecurityService securityService;
    private final ProductRepository productRepository;
    private final RentRepository rentRepository;

    @PostMapping("/rent/{productId}")
    public String addRent(@ModelAttribute("rent") Rent rent, @PathVariable Long productId) {

        if (rent.getEndDate().isBefore(rent.getStartDate())) {
            return "redirect:/";
        }

        var loggedUser = securityService.getSessionUser();

        var optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return "redirect:/";
        }

        Product product = optionalProduct.get();

        if (Objects.equals(product.getOwner(), loggedUser)) {
            return "redirect:/account";
        }

        rent.setRenter(loggedUser);
        rent.setProduct(product);

        rentRepository.save(rent);

        return "redirect:/";

    }

    @GetMapping("/rents")
    public String getRentPage(Model model) {

        var loggedUser = securityService.getSessionUser();

        var rents = rentRepository.findByRenter(loggedUser);

        model.addAttribute("rents", rents);

        return "rents";

    }

    @GetMapping("/rents/pending")
    public String getPendingRentPage(Model model) {

        var loggedUser = securityService.getSessionUser();
        var rents = rentRepository.findByProductOwner(loggedUser);

        model.addAttribute("rents", rents);

        return "pending-rents";

    }

    @PostMapping("/rents/pending/accept/{id}")
    public String acceptRent(@PathVariable Long id) {

        var loggedUser = securityService.getSessionUser();

        var optionalRent = rentRepository.findById(id);

        if (optionalRent.isEmpty()) {
            return "redirect:/";
        }

        if (
                optionalRent.get().getProduct().getOwner().getEmail().equals(
                        loggedUser.getEmail()
                )
        ) {
            optionalRent.get().setApproved(true);
            rentRepository.save(optionalRent.get());
        }

        return "redirect:/rents/pending";

    }

}
