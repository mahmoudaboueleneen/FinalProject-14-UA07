package com.ua07.merchants.model;

import com.ua07.merchants.enums.Category;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "products")
@Builder(setterPrefix = "with")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    //COMMON
    @Id private String id;
    private String name;
    private String merchantId;
    private String description;
    private double price;
    private int stock;
    private Category category;
    private LocalDateTime createdAt;
    private List<Review> reviews;

    //LAPTOPS
    private String processor;
    private String ram;
    private String storage;

    //BOOKS
    private String author;
    private String genre;
    private int pages;

    //JACKETS
    private String size;
    private String material;
    private String color;
}
