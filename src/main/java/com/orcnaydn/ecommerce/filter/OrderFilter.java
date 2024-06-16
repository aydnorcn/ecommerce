package com.orcnaydn.ecommerce.filter;

import com.orcnaydn.ecommerce.entity.Order;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.utils.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderFilter {

    public static Specification<Order> filter(User user, Double minPrice, Double maxPrice,
                                              Integer status) {
        return Specification
                .where(hasUser(user))
                .and(hasPriceGreaterThan(minPrice))
                .and(hasPriceLessThan(maxPrice))
                .and(hasStatus(status));
    }

    public static Specification<Order> hasUser(User user) {
        return (root, query, builder) -> user == null ? builder.conjunction() : builder.equal(root.get("user"), user);
    }

    public static Specification<Order> hasPriceGreaterThan(Double minPrice) {
        return (root, query, builder) -> minPrice == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("totalPrice"), minPrice);
    }

    public static Specification<Order> hasPriceLessThan(Double maxPrice) {
        return (root, query, builder) -> maxPrice == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("totalPrice"), maxPrice);
    }

    public static Specification<Order> hasStatus(Integer status) {
        return (root, query, builder) -> status == null ? builder.conjunction() : builder.equal(root.get("status"), OrderStatus.getById(status));
    }
}
