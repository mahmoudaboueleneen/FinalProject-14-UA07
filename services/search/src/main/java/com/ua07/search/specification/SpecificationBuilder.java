package com.ua07.search.specification;

import com.ua07.search.dto.SearchRequestDTO;
import com.ua07.search.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificationBuilder {
    public static List<Product> filter(List<Product> products, SearchRequestDTO request) {
        ProductSpecification spec = new CategorySpecification(request.getCategory())
                .and(new PriceRangeSpecification(request.getMinPrice(), request.getMaxPrice()))
                .and(new KeywordSpecification(request.getKeyword()));

        return products.stream()
                .filter(spec::isSatisfiedBy)
                .collect(Collectors.toList());
    }
}
