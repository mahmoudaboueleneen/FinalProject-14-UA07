package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateSalesReportRequest extends CommandRequest {
    private YearMonth yearMonth;
}
