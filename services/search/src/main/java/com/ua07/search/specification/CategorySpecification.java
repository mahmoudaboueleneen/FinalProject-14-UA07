package com.ua07.search.specification;

import com.ua07.merchants.model.Product;


import java.util.List;
import java.util.stream.Collectors;

public class CategorySpecification implements ProductSpecification {
    private final String category;

    public CategorySpecification(String category) {
        this.category = category;
    }

    @Override
    public boolean isSatisfiedBy(Product product) {
        if (category == null || category.isEmpty()) {
            return true;
        }
        return category.equalsIgnoreCase(product.getCategory());
    }

    @Override
    public List<Product> filter(List<Product> products) {
        return products.stream()
                .filter(this::isSatisfiedBy)
                .collect(Collectors.toList());
    }

}
