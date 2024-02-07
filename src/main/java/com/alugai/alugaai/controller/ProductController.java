package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.Product;
import com.alugai.alugaai.domain.ProductCategory;
import com.alugai.alugaai.domain.Rent;
import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.repository.ProductCategoryRepository;
import com.alugai.alugaai.repository.ProductRepository;
import com.alugai.alugaai.security.SecurityService;
import com.alugai.alugaai.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final SecurityService securityService;
    private final StorageService storageService;

    @GetMapping({"/products", ""})
    public String getProductsPage(@RequestParam("q") Optional<String> optionalCategoryName, Model model) {

        model.addAttribute("categories", categoryRepository.findAll());

        if (optionalCategoryName.isEmpty()) {
            model.addAttribute("products", productRepository.findAll());
            return "products";
        }

        var optionalRepoCategory = categoryRepository.findByName(optionalCategoryName.get());

        if (optionalRepoCategory.isPresent()) {
            ProductCategory category = optionalRepoCategory.get();
            model.addAttribute("products", category.getProducts());
        }

        return "products";

    }

    @GetMapping("/products/{id}")
    public String getProductPage(Model model, @PathVariable("id") Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            return "redirect:/products";
        }

        model.addAttribute("product", optionalProduct.get());
        model.addAttribute("rent", new Rent());
        return "product";

    }

    @GetMapping("/add/product")
    public String addProductPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add/product")
    public String addProduct(
            @ModelAttribute("product") Product product,
            @RequestPart("product-photo") MultipartFile productImage
    ) {

        Optional<User> optionalLoggedUser = securityService.getSessionUser();

        if (optionalLoggedUser.isPresent()) {

            product.setOwner(optionalLoggedUser.get());

            storageService.store(productImage);
            product.setPhotoPath(productImage.getOriginalFilename());

            productRepository.save(product);

        }

        return "redirect:/products";

    }

}
