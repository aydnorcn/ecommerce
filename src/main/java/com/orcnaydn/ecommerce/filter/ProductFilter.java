package com.orcnaydn.ecommerce.filter;

import com.orcnaydn.ecommerce.entity.Category;
import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.Store;
import org.springframework.data.jpa.domain.Specification;

public class ProductFilter {

    public static Specification<Product> filter(Store store, Category category, Double minPrice, Double maxPrice,
                                                Float minTotalScore, Float maxTotalScore) {
        return Specification
                .where(hasStore(store))
                .and(hasCategory(category))
                .and(hasPriceGreaterThan(minPrice))
                .and(hasPriceLessThan(maxPrice))
                .and(hasTotalScoreGreaterThan(minTotalScore))
                .and(hasTotalScoreGreaterThan(maxTotalScore));
    }

    public static Specification<Product> hasStore(Store store) {
        return (root, query, builder) -> store == null ? builder.conjunction() : builder.equal(root.get("store"), store);
    }

    public static Specification<Product> hasCategory(Category category) {
        return (root, query, builder) -> category == null ? builder.conjunction() : builder.equal(root.get("category"), category);
    }

    public static Specification<Product> hasPriceGreaterThan(Double minPrice) {
        return (root, query, builder) -> minPrice == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasPriceLessThan(Double maxPrice) {
        return (root, query, builder) -> maxPrice == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasTotalScoreGreaterThan(Float minTotalScore) {
        return (root, query, builder) -> minTotalScore == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("totalScore"), minTotalScore);
    }

    public static Specification<Product> hasTotalScoreLessThan(Float maxTotalScore) {
        return (root, query, builder) -> maxTotalScore == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("totalScore"), maxTotalScore);
    }
}
