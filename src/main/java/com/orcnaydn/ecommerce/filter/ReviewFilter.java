package com.orcnaydn.ecommerce.filter;

import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.Review;
import com.orcnaydn.ecommerce.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class ReviewFilter {

    public static Specification<Review> filter(User user, Product product, Integer minScore, Integer maxScore) {
        return Specification
                .where(hasUser(user))
                .and(hasProduct(product))
                .and(hasScoreGreaterThan(minScore))
                .and(hasScoreLessThan(maxScore));
    }

    public static Specification<Review> hasUser(User user) {
        return (root, query, builder) -> user == null ? builder.conjunction() : builder.equal(root.get("user"), user);
    }

    public static Specification<Review> hasProduct(Product product) {
        return (root, query, builder) -> product == null ? builder.conjunction() : builder.equal(root.get("product"), product);
    }

    public static Specification<Review> hasScoreGreaterThan(Integer minScore) {
        return (root, query, builder) -> minScore == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("score"), minScore);
    }

    public static Specification<Review> hasScoreLessThan(Integer maxScore) {
        return (root, query, builder) -> maxScore == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("score"), maxScore);
    }
}
