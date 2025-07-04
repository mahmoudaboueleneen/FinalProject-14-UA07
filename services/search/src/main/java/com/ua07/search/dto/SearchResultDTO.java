package com.ua07.search.dto;

import com.ua07.search.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchResultDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stock;
    private LocalDateTime createdAt;
    private double averageRating;

    public SearchResultDTO(String id, String name, String description, double price, Category category, int stock, LocalDateTime createdAt, double averageRating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category.toString();
        this.stock = stock;
        this.createdAt = createdAt;
        this.averageRating = averageRating;
    }

}
