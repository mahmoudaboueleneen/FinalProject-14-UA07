package com.ua07.search.specification;

import com.ua07.merchants.model.Product;
import org.springframework.cloud.context.named.NamedContextFactory;

public class CategorySpecification implements ProductSpecification {
    private final String category;

    public CategorySpecification(String category) {
        this.category = category;
    }

    @Override
    public boolean isSatisfiedBy(Product product) {
        if (category == null || category.isBlank()) {
            return true;
        }
        return category.equalsIgnoreCase(product.getCategory());
    }

}
