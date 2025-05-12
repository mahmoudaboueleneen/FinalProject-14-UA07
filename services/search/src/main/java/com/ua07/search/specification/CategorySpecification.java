package com.ua07.search.specification;

import com.ua07.search.model.Product;

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
        return category.equalsIgnoreCase(String.valueOf(product.getCategory()));
    }

}
