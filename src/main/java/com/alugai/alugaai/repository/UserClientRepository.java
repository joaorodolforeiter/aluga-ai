package com.alugai.alugaai.repository;

import com.alugai.alugaai.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClientRepository extends JpaRepository<User, Long> {
}
