package com.ua07.transactions.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service", url = "${merchants.service.url}")
public interface StockClient {

    @GetMapping("/{productId}/viewStock")
    Map<String,Object> viewStock(@PathVariable("productId") String productId);

    @GetMapping("/{id}")
    Map<String, Object> getProductById(@PathVariable("id") String id);

    @PutMapping("/{productId}/adjustStock")
    Map<String, Object> adjustStock(
        @PathVariable("productId") String productId,
        @RequestParam("stockChange") int stockChange
    );

}
