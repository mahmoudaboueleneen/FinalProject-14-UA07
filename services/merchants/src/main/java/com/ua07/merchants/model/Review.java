package com.ua07.merchants.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private UUID userId;
    private int rating;
    private String comment;
}