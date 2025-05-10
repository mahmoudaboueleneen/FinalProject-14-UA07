package com.ua07.merchants.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transactions-service", url = "${transactions.service.url}")
public interface OrderClient {
    @GetMapping("/confirmed")
    List<Map<String,Object>> getConfirmedOrders(@RequestParam String startDate, @RequestParam String endDate);
}
