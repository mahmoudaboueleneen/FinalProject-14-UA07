package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStockResponse extends CommandResponse {
    private String id;
    private String name;
    private int stock;
}
