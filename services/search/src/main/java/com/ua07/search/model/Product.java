// mirror of com.ua07.merchants.model.Product .. update whenever the original class is updated
package com.ua07.search.model;


import com.ua07.search.enums.Category;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder(setterPrefix = "with")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    //COMMON
    @Id private UUID id;
    private String name;
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
