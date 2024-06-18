package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.Favorite;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findAllByUser(User user);
    Boolean existsByUserAndProduct(User user, Product product);
    Favorite findByUserAndProduct(User user, Product product);
}
