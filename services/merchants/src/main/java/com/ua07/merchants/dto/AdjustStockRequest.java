package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import java.util.UUID;

import com.ua07.shared.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdjustStockRequest extends CommandRequest {
    private String productId;
    private UUID userId;
    private Role role;
    private int stockChange;
}
