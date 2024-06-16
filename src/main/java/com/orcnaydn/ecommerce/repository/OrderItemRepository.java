package com.orcnaydn.ecommerce.repository;

import com.orcnaydn.ecommerce.entity.OrderItem;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByProductAndUser(Product product, User user);
}
