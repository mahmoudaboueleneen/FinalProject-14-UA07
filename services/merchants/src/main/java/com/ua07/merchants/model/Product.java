package com.ua07.merchants.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;
    private Map<String, Object> additionalAttributes;

    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
        this.stock = builder.stock;
        this.category = builder.category;
        this.additionalAttributes = builder.additionalAttributes;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getCategory() { return category; }
    public Map<String, Object> getAdditionalAttributes() { return additionalAttributes; }

    public static class ProductBuilder {
        private String id;
        private String name;
        private String description;
        private double price;
        private int stock;
        private String category;
        private Map<String, Object> additionalAttributes;

        public ProductBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public ProductBuilder category(String category) {
            this.category = category;
            return this;
        }

        public ProductBuilder additionalAttributes(Map<String, Object> attributes) {
            this.additionalAttributes = attributes;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
