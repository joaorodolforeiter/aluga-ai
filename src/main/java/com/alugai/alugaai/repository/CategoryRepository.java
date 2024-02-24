package com.alugai.alugaai.repository;

import com.alugai.alugaai.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
