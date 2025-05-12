// mirror of com.ua07.merchants.model.Review .. update whenever the original class is updated
package com.ua07.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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