package com.ua07.search.specification;

import com.ua07.search.model.Product;

public class KeywordSpecification implements ProductSpecification {
    private final String keyword;

    public KeywordSpecification(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean isSatisfiedBy(Product product) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }

        String lowerKeyword = keyword.toLowerCase();
        return product.getName().toLowerCase().contains(lowerKeyword)
                || product.getDescription().toLowerCase().contains(lowerKeyword);
    }
}
