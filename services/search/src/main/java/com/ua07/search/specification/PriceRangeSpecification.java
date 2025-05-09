package com.ua07.search.specification;

import com.ua07.merchants.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class PriceRangeSpecification implements ProductSpecification {
        private final Double minPrice;
        private final Double maxPrice;

        public PriceRangeSpecification(Double minPrice, Double maxPrice) {
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
        }

        @Override
        public boolean isSatisfiedBy(Product product) {
            if (minPrice != null && product.getPrice() < minPrice) {
                return false;
            }
            if (maxPrice != null && product.getPrice() > maxPrice) {
                return false;
            }
            return true;
        }

        @Override
        public List<Product> filter(List<Product> products) {
            return products.stream()
                    .filter(this::isSatisfiedBy)
                    .collect(Collectors.toList());
        }
    }

