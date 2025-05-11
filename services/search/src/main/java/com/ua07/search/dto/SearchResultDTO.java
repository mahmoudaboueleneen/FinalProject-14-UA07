package com.ua07.search.dto;

import com.ua07.search.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SearchResultDTO {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stock;

    public SearchResultDTO(UUID id, String name, String description, double price, Category category, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category.toString();
        this.stock = stock;
    }

}
