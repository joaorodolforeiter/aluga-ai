package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.Product;
import com.alugai.alugaai.domain.Rent;
import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.repository.ProductRepository;
import com.alugai.alugaai.repository.RentRepository;
import com.alugai.alugaai.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

        var optionalLoggedUser = securityService.getSessionUser();

        if (optionalLoggedUser.isEmpty()) {
            return "redirect:/";
        }

        var optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            return "redirect:/";
        }

        User user = optionalLoggedUser.get();
        Product product = optionalProduct.get();

        if (product.getOwner().equals(user)) {
            return "account";
        }

        rent.setRenter(user);
        rent.setProduct(product);

        rentRepository.save(rent);

        return "redirect:/";

    }

}
