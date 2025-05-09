package com.ua07.search.specification;

import com.ua07.merchants.model.Product;
import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder {
    private Double minPrice;
    private Double maxPrice;
    private String category;

    public SpecificationBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public SpecificationBuilder withPriceRange(Double minPrice, Double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        return this;
    }

    public List<Product> buildAndFilter(List<Product> products) {
        List<Product> result = new ArrayList<>();

        for (Product product : products) {
            // Check category filter
            if (category != null && !category.isEmpty() &&
                    !category.equalsIgnoreCase(product.getCategory())) {
                continue;
            }

            // Check price filter
            if (minPrice != null && product.getPrice() < minPrice) {
                continue;
            }
            if (maxPrice != null && product.getPrice() > maxPrice) {
                continue;
            }

            result.add(product);
        }

        return result;
    }
}