package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStockRequest extends CommandRequest {
    private String productId;
}
