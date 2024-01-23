package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.Product;
import com.alugai.alugaai.domain.ProductCategory;
import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.repository.ProductCategoryRepository;
import com.alugai.alugaai.repository.ProductRepository;
import com.alugai.alugaai.security.SecurityService;
import com.alugai.alugaai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ProductController {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final SecurityService securityService;

    @GetMapping("/products")
    public String getProductsPage(@RequestParam("q") Optional<String> optionalCategory, Model model) {

        model.addAttribute("categories", categoryRepository.findAll());

        if (optionalCategory.isPresent()) {
            ProductCategory category = categoryRepository.findByName(optionalCategory.get()).get();
            model.addAttribute("products", category.getProducts());
        }

        if (optionalCategory.isEmpty()) {
            model.addAttribute("products", productRepository.findAll());
        }


        return "products";

    }

    @GetMapping("/products/{id}")
    public String getProductPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productRepository.findById(id).get());
        return "product";
    }

    @GetMapping("/add/product")
    public String addProductPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add/product")
    public String addProduct(@ModelAttribute("product") Product product) {

        Optional<User> optionalLoggedUser = securityService.getSessionUser();

        if (optionalLoggedUser.isPresent()) {

            product.setOwner(
                    optionalLoggedUser.get()
            );

            productRepository.save(product);

        }

        return "redirect:/products";

    }

}
