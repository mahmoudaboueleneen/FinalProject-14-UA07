package com.ua07.merchants.dto;

import com.ua07.merchants.command.GenerateSalesReportCommand;
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
public class GenerateSalesReportResponse extends CommandResponse {
    private List<GenerateSalesReportCommand.ProductSalesReport> report;
}




