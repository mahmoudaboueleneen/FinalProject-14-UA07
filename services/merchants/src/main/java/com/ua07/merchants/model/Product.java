package com.ua07.merchants.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.UUID;

@Document(collection = "products")
@Builder(setterPrefix = "with")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String category;

    private Map<String, Object> additionalAttributes;

}
