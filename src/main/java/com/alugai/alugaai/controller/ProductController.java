package com.alugai.alugaai.controller;

import com.alugai.alugaai.domain.Product;
import com.alugai.alugaai.domain.Rent;
import com.alugai.alugaai.domain.User;
import com.alugai.alugaai.repository.ProductCategoryRepository;
import com.alugai.alugaai.repository.ProductRepository;
import com.alugai.alugaai.security.SecurityService;
import com.alugai.alugaai.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public String getProductsPage(
            @RequestParam(value = "category", defaultValue = "") String categoryName,
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(defaultValue = "1") Integer page,
            Model model
    ) {

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("selectedCategory", categoryName);

        return "products";

    }

    @GetMapping("products/list")
    public String getProductsList(
            @RequestParam(value = "category", defaultValue = "") String optionalCategoryName,
            @RequestParam(value = "q", defaultValue = "") String optionalQuery,
            @RequestParam(defaultValue = "1") Integer page,
            Model model
    ) {

        model.addAttribute("category", optionalCategoryName);
        model.addAttribute("page", page);
        model.addAttribute("query", optionalQuery);

        if (!optionalQuery.isEmpty()) {
            model.addAttribute(
                    "products",
                    productRepository.findByNameContaining(
                            optionalQuery,
                            Pageable.ofSize(30).withPage(page - 1)
                    )
            );
            return "fragments/product-list";
        }

        if (!optionalCategoryName.isEmpty()) {
            model.addAttribute("products",
                    productRepository.findByCategoryName(
                            optionalCategoryName,
                            Pageable.ofSize(30).withPage(page - 1)
                    )
            );
            return "fragments/product-list";
        }

        model.addAttribute("products",
                productRepository.findAll(
                        Pageable.ofSize(30).withPage(page - 1)
                )
        );

        return "fragments/product-list";

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
