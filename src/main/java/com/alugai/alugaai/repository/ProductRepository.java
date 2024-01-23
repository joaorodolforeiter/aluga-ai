package com.alugai.alugaai.repository;

import com.alugai.alugaai.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
