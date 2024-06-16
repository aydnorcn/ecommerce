package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.Review;
import com.orcnaydn.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAll(Specification<Review> specs, Pageable pageable);
    Optional<Review> findByUserAndProduct(User user, Product product);
}
