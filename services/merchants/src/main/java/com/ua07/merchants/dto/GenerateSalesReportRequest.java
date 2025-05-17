package com.ua07.merchants.dto;

import com.ua07.shared.command.CommandRequest;
import java.time.YearMonth;
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
public class GenerateSalesReportRequest extends CommandRequest {
    private Role role;
    private YearMonth yearMonth;
}
