package com.alugai.alugaai.repository;

import com.alugai.alugaai.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String name, Pageable pageable);

    List<Product> findByCategoryName(String name);

    List<Product> findByCategoryName(String name, Pageable pageable);

}
