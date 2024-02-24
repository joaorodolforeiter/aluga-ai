package com.alugai.alugaai.controller;

import com.alugai.alugaai.dto.CategoryRegistrationDto;
import com.alugai.alugaai.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("add/category")
    public String getAddCategoryForm(Model model) {

        model.addAttribute("category", new CategoryRegistrationDto());
        return "add-category-form";

    }

    @PostMapping("add/category")
    public String addCategory(
            @ModelAttribute("category") CategoryRegistrationDto category
    ) {

        categoryService.create(category);
        return "redirect:/";

    }

}
