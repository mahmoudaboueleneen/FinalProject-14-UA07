package com.ua07.merchants.controller;

import com.ua07.merchants.client.OrderClient;
import com.ua07.merchants.command.AdjustStockCommand;
import com.ua07.merchants.command.GenerateSalesReportCommand;
import com.ua07.merchants.command.RecommendProductsCommand;
import com.ua07.merchants.dto.*;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.CommandExecutor;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class MerchantController {

    private final ProductRepository productRepository;
    private final CommandExecutor commandExecutor;
    private final OrderClient orderClient;

    @Autowired
    public MerchantController(
            OrderClient orderClient,
            CommandExecutor commandExecutor,
            ProductRepository productRepository) {
        this.orderClient = orderClient;
        this.commandExecutor = commandExecutor;
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            Product updatedProduct =
                    Product.builder()
                            .withId(product.getId())
                            .withName(updated.getName())
                            .withDescription(updated.getDescription())
                            .withPrice(updated.getPrice())
                            .withStock(updated.getStock())
                            .withCategory(updated.getCategory())
                            .withAdditionalAttributes(updated.getAdditionalAttributes())
                            .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @PutMapping("/{productID}/adjustStock")
    public AdjustStockResponse adjustStock(
            @PathVariable UUID productID, @RequestParam int stockChange) {
        AdjustStockRequest request = new AdjustStockRequest(productID, stockChange);
        AdjustStockCommand command = new AdjustStockCommand(productRepository);
        return commandExecutor.execute(command, request);
    }

    @GetMapping("/{id}/recommendations")
    public RecommendProductsResponse recommendProducts(@PathVariable UUID id) {
        RecommendProductsRequest request = new RecommendProductsRequest(id);
        RecommendProductsCommand command = new RecommendProductsCommand(productRepository);
        return commandExecutor.execute(command, request);
    }

    @GetMapping("/salesReport")
    public GenerateSalesReportResponse getSalesReport(
            @RequestParam("yearMonth") @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
        GenerateSalesReportRequest request = new GenerateSalesReportRequest(yearMonth);
        GenerateSalesReportCommand command = new GenerateSalesReportCommand(orderClient);
        return commandExecutor.execute(command, request);
    }
}
