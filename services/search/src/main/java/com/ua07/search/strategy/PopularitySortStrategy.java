package com.ua07.search.strategy;

import com.ua07.merchants.model.Product;

import java.util.Comparator;
import java.util.List;

public class PopularitySortStrategy implements SortingStrategy{
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingInt(Product::getStock).reversed())
                .toList();
    }
}
