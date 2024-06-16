package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {

    Page<Store> findAll(Specification<Store> specs, Pageable pageable);
    Boolean existsByNameIgnoreCase(String name);
}
