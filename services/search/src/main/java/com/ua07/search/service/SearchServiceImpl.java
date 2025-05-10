package com.ua07.search.service;

import com.ua07.search.client.ProductClient;
import com.ua07.search.dto.SearchRequestDTO;
import com.ua07.search.model.Product;
import com.ua07.search.specification.SpecificationBuilder;
import com.ua07.search.strategy.ReflectionBasedStrategyFactory;
import com.ua07.search.strategy.SortType;
import com.ua07.search.strategy.SortingStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ProductClient productClient;
    private final ReflectionBasedStrategyFactory sortingStrategyFactory;

    public SearchServiceImpl(ProductClient productClient, ReflectionBasedStrategyFactory sortingStrategyFactory) {
        this.productClient = productClient;
        this.sortingStrategyFactory = sortingStrategyFactory;
    }

    @Override
    @Cacheable(value = "searchResults", key = "#request.hashCode()")
    public List<Product> searchProducts(SearchRequestDTO request) {
        List<Product> products = productClient.getAllProducts();

        List<Product> filtered = SpecificationBuilder.filter(products, request);

        SortType sortType = mapSortType(request.getSortBy());
        SortingStrategy strategy = sortingStrategyFactory.getStrategy(sortType);

        return strategy.sort(filtered);
    }

    private SortType mapSortType(String sortBy) {
        return switch (sortBy == null ? "" : sortBy.toLowerCase()) {
            case "popularity" -> SortType.POPULARITY;
            case "recency" -> SortType.RECENCY;
            default -> SortType.PRICE;
        };
    }
}
