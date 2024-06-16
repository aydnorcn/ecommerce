package com.orcnaydn.ecommerce.filter;

import com.orcnaydn.ecommerce.entity.Store;
import com.orcnaydn.ecommerce.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class StoreFilter {

    public static Specification<Store> filter(User user, Float minTotalScore, Float maxTotalScore) {
        return Specification
                .where(hasUser(user))
                .and(hasTotalScoreGreaterThan(minTotalScore))
                .and(hasTotalScoreLessThan(maxTotalScore));
    }


    public static Specification<Store> hasUser(User user) {
        return (root, query, builder) -> user == null ? builder.conjunction() : builder.equal(root.get("user"), user);
    }

    public static Specification<Store> hasTotalScoreGreaterThan(Float minTotalScore) {
        return (root, query, builder) -> minTotalScore == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("totalScore"), minTotalScore);
    }

    public static Specification<Store> hasTotalScoreLessThan(Float maxTotalScore) {
        return (root, query, builder) -> maxTotalScore == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("totalScore"), maxTotalScore);
    }


}
