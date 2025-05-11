package com.ua07.search.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class SearchRequestDTO {
    private String keyword;
    private String category;

    @Min(value = 0, message = "minPrice must be >= 0")
    private Double minPrice;

    @Min(value = 0, message = "maxPrice must be >= 0")
    private Double maxPrice;

    @Pattern(regexp = "^(price|popularity|recency)?$", message = "sortBy must be price, popularity, or recency")
    private String sortBy;

    public SearchRequestDTO() {}

    public SearchRequestDTO(String keyword, String category, Double minPrice, Double maxPrice, String sortBy) {
        this.keyword = keyword;
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortBy = sortBy;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
