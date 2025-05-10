package com.ua07.search.service;

import com.ua07.merchants.model.Product;
import com.ua07.search.dto.SearchRequestDTO;

import java.util.List;

public interface SearchService {
    List<Product> searchProducts(SearchRequestDTO request);
}
