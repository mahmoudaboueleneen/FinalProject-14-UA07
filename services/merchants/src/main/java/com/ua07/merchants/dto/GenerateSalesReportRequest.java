package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateSalesReportRequest extends CommandRequest {
    private YearMonth yearMonth;
}


