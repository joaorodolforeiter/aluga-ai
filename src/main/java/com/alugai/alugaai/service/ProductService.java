package com.alugai.alugaai.service;

import com.alugai.alugaai.dto.ProductRegistrationDto;
import com.alugai.alugaai.model.Product;
import com.alugai.alugaai.repository.ProductRepository;
import com.alugai.alugaai.security.CurrentUserProvider;
import com.alugai.alugaai.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CurrentUserProvider currentUserProvider;
    private final StorageService storageService;

    public Product create(ProductRegistrationDto productDto) {

        var loggedUser = currentUserProvider.getSessionUser();

        var fileId = storageService.store(
                productDto.getPhoto()
        );

        Product product = Product
                .builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .owner(loggedUser)
                .category(productDto.getCategory())
                .state(productDto.getState())
                .city(productDto.getCity())
                .photoPath(fileId)
                .build();

        return productRepository.save(product);

    }

    public Page<Product> findByNameContaining(String query, Pageable pageable) {
        return productRepository.findByNameContaining(query, pageable);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findByCategoryName(String categoryName, Pageable pageable) {
        return productRepository.findByCategoryName(categoryName, pageable);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
