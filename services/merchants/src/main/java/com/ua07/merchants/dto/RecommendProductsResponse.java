package com.ua07.merchants.dto;

import com.ua07.merchants.model.Product;
import com.ua07.shared.command.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendProductsResponse extends CommandResponse {
    List<Product> recommendedProducts;
}
