package com.ua07.search.specification;

import com.ua07.merchants.model.Product;

public class PriceRangeSpecification implements ProductSpecification {
    private final Double minPrice;
    private final Double maxPrice;

    public PriceRangeSpecification(Double minPrice, Double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean isSatisfiedBy(Product product) {
        double price = product.getPrice();

        if (minPrice != null && price < minPrice) {
            return false;
        }

        if (maxPrice != null && price > maxPrice) {
            return false;
        }

        return true;
    }
}
