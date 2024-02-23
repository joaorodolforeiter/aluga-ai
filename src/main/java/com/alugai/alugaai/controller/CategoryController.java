package com.alugai.alugaai.controller;

import com.alugai.alugaai.model.ProductCategory;
import com.alugai.alugaai.repository.ProductCategoryRepository;
import com.alugai.alugaai.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final StorageService storageService;
    private final ProductCategoryRepository categoryRepository;

    @GetMapping("add/category")
    public String getAddCategoryForm(Model model) {
        model.addAttribute("category", new ProductCategory());
        return "add-category-form";
    }

    @PostMapping("add/category")
    public String addCategory(
            @ModelAttribute("category") ProductCategory category,
            @RequestPart("image") MultipartFile image
    ) {

        storageService.store(image);
        category.setImagePath(image.getOriginalFilename());
        categoryRepository.save(category);

        return "redirect:/";

    }

}
