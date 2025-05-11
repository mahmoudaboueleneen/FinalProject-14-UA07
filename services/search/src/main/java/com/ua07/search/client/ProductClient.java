package com.ua07.search.client;

import com.ua07.search.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "merchants-service", url = "${merchants.service.url}")
public interface ProductClient {
    @GetMapping("/products")
    List<Product> getAllProducts();

//    @GetMapping("/products/search")
//    List<Product> searchProductsByKeyword(@RequestParam("keyword") String keyword);
}
