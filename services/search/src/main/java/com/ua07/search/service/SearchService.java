package com.ua07.search.service;

import com.ua07.search.dto.SearchRequestDTO;
import com.ua07.search.model.Product;

import java.util.List;

public interface SearchService {
    List<Product> searchProducts(SearchRequestDTO request);
}
