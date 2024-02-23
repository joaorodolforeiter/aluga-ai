package com.alugai.alugaai.repository;

import com.alugai.alugaai.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
