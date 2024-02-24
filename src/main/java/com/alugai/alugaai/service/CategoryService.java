package com.alugai.alugaai.service;

import com.alugai.alugaai.dto.CategoryRegistrationDto;
import com.alugai.alugaai.model.Category;
import com.alugai.alugaai.repository.CategoryRepository;
import com.alugai.alugaai.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final StorageService storageService;

    public Category create(CategoryRegistrationDto categoryDto) {

        var fileId = storageService.store(categoryDto.getPhoto());

        var category = Category
                .builder()
                .name(categoryDto.getName())
                .imagePath(fileId)
                .build();

        return categoryRepository.save(category);

    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
