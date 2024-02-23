package com.alugai.alugaai.repository;

import com.alugai.alugaai.domain.Rent;
import com.alugai.alugaai.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findByRenter(User renter);

    List<Rent> findByProductOwner(User renter);

}
