package com.alugai.alugaai.repository;

import com.alugai.alugaai.domain.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {
}
