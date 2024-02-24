package com.alugai.alugaai.controller;

import com.alugai.alugaai.dto.ProductRegistrationDto;
import com.alugai.alugaai.dto.RentRegistrationDto;
import com.alugai.alugaai.model.Product;
import com.alugai.alugaai.security.CurrentUserProvider;
import com.alugai.alugaai.service.CategoryService;
import com.alugai.alugaai.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping({"/products", ""})
    public String getProductsPage(
            @RequestParam(value = "category", defaultValue = "") String categoryName,
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(defaultValue = "1") Integer page,
            Model model
    ) {

        model
                .addAttribute("categories", categoryService.findAll())
                .addAttribute("query", query)
                .addAttribute("page", page)
                .addAttribute("selectedCategory", categoryName);


        return "products";

    }

    @GetMapping("products/list")
    public String getProductsList(
            @RequestParam(value = "category", defaultValue = "") String optionalCategoryName,
            @RequestParam(value = "q", defaultValue = "") String optionalQuery,
            @RequestParam(defaultValue = "1") Integer page,
            Model model
    ) {

        model
                .addAttribute("category", optionalCategoryName)
                .addAttribute("page", page)
                .addAttribute("query", optionalQuery);

        if (!optionalQuery.isEmpty()) {
            model.addAttribute(
                    "products",
                    productService.findByNameContaining(
                            optionalQuery,
                            Pageable.ofSize(30).withPage(page - 1)
                    )
            );
            return "fragments/product-list";
        }

        if (!optionalCategoryName.isEmpty()) {
            model.addAttribute("products",
                    productService.findByCategoryName(
                            optionalCategoryName,
                            Pageable.ofSize(30).withPage(page - 1)
                    )
            );
            return "fragments/product-list";
        }

        model.addAttribute("products",
                productService.findAll(
                        Pageable.ofSize(30).withPage(page - 1)
                )
        );

        return "fragments/product-list";

    }

    @GetMapping("/products/{id}")
    public String getProductPage(Model model, @PathVariable("id") Long id) {

        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            return "redirect:/products";
        }

        model.addAttribute("product", optionalProduct.get());
        model.addAttribute("rent", new RentRegistrationDto());

        return "product";

    }

    @GetMapping("/add/product")
    public String addProductPage(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", new ProductRegistrationDto());
        return "add-product";

    }

    @PostMapping("/add/product")
    public String addProduct(
            @ModelAttribute("product") ProductRegistrationDto productRegistrationDto
    ) {

        productService.create(productRegistrationDto);
        return "redirect:/products";

    }

    @DeleteMapping(value = "/products/{id}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {

        var optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            return "Erro ao remover o item";
        }

        var loggedUser = currentUserProvider.getSessionUser();

        if (
                Objects.equals(
                        optionalProduct.get().getOwner().getEmail(),
                        loggedUser.getEmail()
                )
        ) {
            productService.deleteById(id);
        }

        return "Item removido com sucesso";

    }


}
