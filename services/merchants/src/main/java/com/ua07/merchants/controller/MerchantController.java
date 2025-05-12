package com.ua07.merchants.controller;

import com.ua07.merchants.dto.*;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class MerchantController {

    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping("/laptops")
    public Product createProductLaptop(@RequestBody Product product) {
        return merchantService.createProductLaptop(product);
    }

    @PostMapping("/books")
    public Product createProductBook(@RequestBody Product product) {
        return merchantService.createProductBook(product);
    }

    @PostMapping("/jackets")
    public Product createProductJacket(@RequestBody Product product) {
        return merchantService.createProductJacket(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return merchantService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return merchantService.getProductById(id);
    }

    @PutMapping("/laptops/{id}")
    public Product updateProductLaptop(@PathVariable String id, @RequestBody Product updated) {
        return merchantService.updateProductLaptop(id, updated);
    }

    @PutMapping("/books/{id}")
    public Product updateProductBook(@PathVariable String id, @RequestBody Product updated) {
        return merchantService.updateProductBook(id, updated);
    }

    @PutMapping("/jackets/{id}")
    public Product updateProductJacket(@PathVariable String id, @RequestBody Product updated) {
        return merchantService.updateProductJacket(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        merchantService.deleteProduct(id);
    }

    @GetMapping("/{productId}/viewStock")
    public ViewStockResponse viewStock(@PathVariable String productId) {
        return merchantService.viewStock(productId);
    }

    @PutMapping("/{productId}/adjustStock")
    public AdjustStockResponse adjustStock(
            @PathVariable String productId,
            @RequestParam int stockChange
    ) {
        return merchantService.adjustStock(productId, stockChange);
    }

    @GetMapping("/salesReport")
    public GenerateSalesReportResponse getSalesReport(
            @RequestParam("yearMonth")
            @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth
    ) {
        return merchantService.getSalesReport(yearMonth);
    }

    @PostMapping("/{productId}/addReview")
    public AddReviewResponse addReview(
            @PathVariable String productId,
            @RequestHeader(value = "User-ID", required = true) String userId,
            @RequestParam int rating,
            @RequestParam String comment
    ) {
        return merchantService.addReview(productId, userId, rating, comment);
    }

}