package com.ua07.search.dto;

import java.util.UUID;

public class SearchResultDTO {
    private String name;
    private String description;
    private double price;
    private String category;
    private int stock;

    public SearchResultDTO(UUID id, String name, String description, double price, String category, int stock) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
