package com.ua07.search.strategy;

import com.ua07.merchants.model.Product;

import java.util.List;

public interface SortingStrategy {
    List<Product> sort(List<Product> products);
}
