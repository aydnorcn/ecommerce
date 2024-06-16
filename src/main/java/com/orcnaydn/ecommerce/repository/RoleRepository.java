package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Boolean existsByName(String name);
    Optional<Role> findByName(String name);
}
