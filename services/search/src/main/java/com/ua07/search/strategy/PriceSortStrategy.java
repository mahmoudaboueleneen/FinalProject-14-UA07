package com.ua07.search.strategy;

import com.ua07.search.model.Product;

import java.util.Comparator;
import java.util.List;

public class PriceSortStrategy implements SortingStrategy{
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .toList();
    }
}
