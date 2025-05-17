package com.ua07.merchants.controller;

import com.ua07.merchants.dto.*;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.service.MerchantService;
import com.ua07.shared.auth.AuthConstants;
import com.ua07.shared.enums.Role;
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
    public Product createProductLaptop(
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role,
            @RequestBody Product product) {
        return merchantService.createProductLaptop(userId, role, product);
    }

    @PostMapping("/books")
    public Product createProductBook(
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role,
            @RequestBody Product product) {
        return merchantService.createProductBook(userId, role, product);
    }

    @PostMapping("/jackets")
    public Product createProductJacket(
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role,
            @RequestBody Product product) {
        return merchantService.createProductJacket(userId, role, product);
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
    public Product updateProductLaptop(
            @PathVariable String id,
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role,
            @RequestBody Product updated) {
        return merchantService.updateProductLaptop(id, userId, role, updated);
    }

    @PutMapping("/books/{id}")
    public Product updateProductBook(
            @PathVariable String id,
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role,
            @RequestBody Product updated) {
        return merchantService.updateProductBook(id, userId, role, updated);
    }

    @PutMapping("/jackets/{id}")
    public Product updateProductJacket(
            @PathVariable String id,
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role,
            @RequestBody Product updated) {
        return merchantService.updateProductJacket(id, userId, role, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable String id,
            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role
    ) {
        merchantService.deleteProduct(id, userId, role);
    }

    @GetMapping("/{productId}/viewStock")
    public ViewStockResponse viewStock(@PathVariable String productId) {
        return merchantService.viewStock(productId);
    }

    @PutMapping("/{productId}/adjustStock")
    public AdjustStockResponse adjustStock(
            @PathVariable String productId,
            @RequestParam int stockChange
//            @RequestHeader(AuthConstants.USER_ID_HEADER) UUID userId,
//            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role
    ) {
//        return merchantService.adjustStock(productId, userId, role, stockChange);
        return merchantService.adjustStock(productId, stockChange);
    }

    @GetMapping("/salesReport")
    public GenerateSalesReportResponse getSalesReport(
            @RequestParam("yearMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
            @RequestHeader(AuthConstants.USER_ROLE_HEADER) Role role
    ) {
        return merchantService.getSalesReport(role, yearMonth);
    }

    @PostMapping("/{productId}/addReview")
    public AddReviewResponse addReview(
            @PathVariable String productId,
            @RequestHeader(value = AuthConstants.USER_ID_HEADER, required = true) UUID userId,
            @RequestParam int rating,
            @RequestParam String comment
    ) {
        return merchantService.addReview(productId, userId, rating, comment);
    }

}