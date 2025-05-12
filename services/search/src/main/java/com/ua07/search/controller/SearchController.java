package com.ua07.search.controller;

import com.ua07.search.dto.SearchRequestDTO;
import com.ua07.search.dto.SearchResultDTO;
import com.ua07.search.model.Product;
import com.ua07.search.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<SearchResultDTO> searchProducts(@ModelAttribute SearchRequestDTO request) {
        List<Product> products = searchService.searchProducts(request);

        if (request.getMinPrice() != null && request.getMaxPrice() != null &&
                request.getMinPrice() > request.getMaxPrice()) {
            request.setMinPrice(null);
            request.setMaxPrice(null);
        }

        return products.stream()
                .map(product -> new SearchResultDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getStock()
                ))
                .collect(Collectors.toList());
    }
}
