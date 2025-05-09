package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendProductsRequest extends CommandRequest {
    private UUID productId;
}
