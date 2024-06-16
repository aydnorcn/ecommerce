package com.orcnaydn.ecommerce.filter;

import com.orcnaydn.ecommerce.entity.Product;
import com.orcnaydn.ecommerce.entity.Question;
import com.orcnaydn.ecommerce.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class QuestionFilter {

    public static Specification<Question> filter(User user, Product product) {
        return Specification
                .where(hasUser(user))
                .and(hasProduct(product));
    }

    public static Specification<Question> hasUser(User user) {
        return (root, query, builder) -> user == null ? builder.conjunction() : builder.equal(root.get("user"), user);
    }

    public static Specification<Question> hasProduct(Product product) {
        return (root, query, builder) -> product == null ? builder.conjunction() : builder.equal(root.get("product"), product);
    }
}
