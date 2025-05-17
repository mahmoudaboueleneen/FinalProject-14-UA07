package com.ua07.merchants.command;

import com.ua07.merchants.client.OrderClient;
import com.ua07.merchants.dto.GenerateSalesReportRequest;
import com.ua07.merchants.dto.GenerateSalesReportResponse;
import com.ua07.shared.command.Command;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.ua07.shared.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GenerateSalesReportCommand
        implements Command<GenerateSalesReportRequest, GenerateSalesReportResponse> {

    private final OrderClient orderClient;

    public GenerateSalesReportCommand(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    @Override
    public GenerateSalesReportResponse execute(GenerateSalesReportRequest request) {
        if (request.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Admin");
        }

        LocalDate startDate = request.getYearMonth().atDay(1);
        LocalDate endDate = request.getYearMonth().atEndOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        List<Map<String, Object>> orders =
                orderClient.getConfirmedOrders(
                        formatter.format(startDate), formatter.format(endDate));

        Map<String, ProductSalesData> report = new HashMap<>();

        for (Map<String, Object> order : orders) {
            List<Map<String, Object>> lineItems =
                    (List<Map<String, Object>>) order.get("lineItems");
            for (Map<String, Object> item : lineItems) {
                String productId = String.valueOf(item.get("productId"));
                int count = ((Number) item.get("count")).intValue();
                double unitCost = ((Number) item.get("unitCost")).doubleValue();

                ProductSalesData data =
                        report.getOrDefault(productId, new ProductSalesData(0, 0.0));
                data.totalQuantity += count;
                data.totalRevenue += count * unitCost;

                report.put(productId, data);
            }
        }

        List<ProductSalesReport> reportList = new ArrayList<>();
        for (Map.Entry<String, ProductSalesData> entry : report.entrySet()) {
            reportList.add(
                    new ProductSalesReport(
                            entry.getKey(),
                            entry.getValue().totalQuantity,
                            entry.getValue().totalRevenue));
        }

        return new GenerateSalesReportResponse(reportList);
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException(
                "Undo not supported for GenerateSalesReportCommand");
    }

    // INNER CLASSES

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSalesReport {
        private String productId;
        private int totalQuantity;
        private double totalRevenue;
    }

    @AllArgsConstructor
    private static class ProductSalesData {
        int totalQuantity;
        double totalRevenue;
    }
}
