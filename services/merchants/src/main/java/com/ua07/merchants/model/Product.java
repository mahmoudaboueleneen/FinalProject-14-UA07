package com.ua07.merchants.model;

import com.ua07.merchants.enums.Category;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String merchantId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private Category category;
    private LocalDateTime createdAt;
    private List<Review> reviews;
    private double averageRating;

    // LAPTOPS
    private String processor;
    private String ram;
    private String storage;

    // BOOKS
    private String author;
    private String genre;
    private Integer pages;

    // JACKETS
    private String size;
    private String material;
    private String color;

    // Builder

    public static class Builder {
        private final Product product;

        public Builder() {
            product = new Product();
        }

        public Builder withId(String id) {
            product.setId(id);
            return this;
        }

        public Builder withMerchantId(String merchantId) {
            product.setMerchantId(merchantId);
            return this;
        }

        public Builder withName(String name) {
            product.setName(name);
            return this;
        }

        public Builder withDescription(String description) {
            product.setDescription(description);
            return this;
        }

        public Builder withPrice(double price) {
            product.setPrice(price);
            return this;
        }

        public Builder withStock(int stock) {
            product.setStock(stock);
            return this;
        }

        public Builder withCategory(Category category) {
            product.setCategory(category);
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            product.setCreatedAt(createdAt);
            return this;
        }

        public Builder withReviews(List<Review> reviews) {
            product.setReviews(reviews);
            return this;
        }

        public Builder withAverageRating(double averageRating) {
            product.setAverageRating(averageRating);
            return this;
        }

        public Builder withProcessor(String processor) {
            product.setProcessor(processor);
            return this;
        }

        public Builder withRam(String ram) {
            product.setRam(ram);
            return this;
        }

        public Builder withStorage(String storage) {
            product.setStorage(storage);
            return this;
        }

        public Builder withAuthor(String author) {
            product.setAuthor(author);
            return this;
        }

        public Builder withGenre(String genre) {
            product.setGenre(genre);
            return this;
        }

        public Builder withPages(Integer pages) {
            product.setPages(pages);
            return this;
        }

        public Builder withSize(String size) {
            product.setSize(size);
            return this;
        }

        public Builder withMaterial(String material) {
            product.setMaterial(material);
            return this;
        }

        public Builder withColor(String color) {
            product.setColor(color);
            return this;
        }

        public Product build() {
            return product;
        }
    }
}

