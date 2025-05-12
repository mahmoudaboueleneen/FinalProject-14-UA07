package com.ua07.merchants.service;

import com.ua07.merchants.client.OrderClient;
import com.ua07.merchants.command.AddReviewCommand;
import com.ua07.merchants.command.AdjustStockCommand;
import com.ua07.merchants.command.GenerateSalesReportCommand;
import com.ua07.merchants.command.ViewStockCommand;
import com.ua07.merchants.dto.*;
import com.ua07.merchants.enums.Category;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.model.Review;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class MerchantService {

    private final ProductRepository productRepository;
    private final CommandExecutor commandExecutor;
    private final OrderClient orderClient;

    @Autowired
    public MerchantService(ProductRepository productRepository, CommandExecutor commandExecutor, OrderClient orderClient) {
        this.productRepository = productRepository;
        this.commandExecutor  = commandExecutor;
        this.orderClient = orderClient;
    }

    public Product createProductLaptop(Product product) {
        Product createdProduct = Product.builder()
                .withId(UUID.randomUUID().toString())
                .withName(product.getName())
                .withDescription(product.getDescription())
                .withPrice(product.getPrice())
                .withStock(product.getStock())
                .withCategory(Category.LAPTOPS)
                .withCreatedAt(LocalDateTime.now())
                .withReviews(new ArrayList<>())
                .withAverageRating(0.0)
                .withProcessor(product.getProcessor())
                .withRam(product.getRam())
                .withStorage(product.getStorage())
                .build();

        return productRepository.save(createdProduct);
    }

    public Product createProductBook(Product product) {
        Product createdProduct = Product.builder()
                .withId(UUID.randomUUID().toString())
                .withName(product.getName())
                .withDescription(product.getDescription())
                .withPrice(product.getPrice())
                .withStock(product.getStock())
                .withCategory(Category.BOOKS)
                .withCreatedAt(LocalDateTime.now())
                .withReviews(new ArrayList<>())
                .withAverageRating(0.0)
                .withAuthor(product.getAuthor())
                .withGenre(product.getGenre())
                .withPages(product.getPages())
                .build();

        return productRepository.save(createdProduct);
    }

    public Product createProductJacket(Product product) {
        Product createdProduct = Product.builder()
                .withId(UUID.randomUUID().toString())
                .withName(product.getName())
                .withDescription(product.getDescription())
                .withPrice(product.getPrice())
                .withStock(product.getStock())
                .withCategory(Category.JACKETS)
                .withCreatedAt(LocalDateTime.now())
                .withReviews(new ArrayList<>())
                .withAverageRating(0.0)
                .withSize(product.getSize())
                .withMaterial(product.getMaterial())
                .withColor(product.getColor())
                .build();

        return productRepository.save(createdProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProductLaptop(String id, Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            Product updatedProduct = Product.builder()
                    .withId(product.getId())
                    .withName(updated.getName())
                    .withDescription(updated.getDescription())
                    .withPrice(updated.getPrice())
                    .withStock(updated.getStock())
                    .withCategory(updated.getCategory())
                    .withCreatedAt(updated.getCreatedAt())
                    .withReviews(updated.getReviews())
                    .withAverageRating(updated.getAverageRating())
                    .withProcessor(updated.getProcessor())
                    .withRam(updated.getRam())
                    .withStorage(updated.getStorage())
                    .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProductBook(String id, Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            Product updatedProduct = Product.builder()
                    .withId(product.getId())
                    .withName(updated.getName())
                    .withDescription(updated.getDescription())
                    .withPrice(updated.getPrice())
                    .withStock(updated.getStock())
                    .withCategory(updated.getCategory())
                    .withCreatedAt(updated.getCreatedAt())
                    .withReviews(updated.getReviews())
                    .withAverageRating(updated.getAverageRating())
                    .withAuthor(updated.getAuthor())
                    .withGenre(updated.getGenre())
                    .withPages(updated.getPages())
                    .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProductJacket(String id, Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            Product updatedProduct = Product.builder()
                    .withId(product.getId())
                    .withName(updated.getName())
                    .withDescription(updated.getDescription())
                    .withPrice(updated.getPrice())
                    .withStock(updated.getStock())
                    .withCategory(updated.getCategory())
                    .withCreatedAt(updated.getCreatedAt())
                    .withReviews(updated.getReviews())
                    .withAverageRating(updated.getAverageRating())
                    .withSize(updated.getSize())
                    .withMaterial(updated.getMaterial())
                    .withColor(updated.getColor())
                    .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public ViewStockResponse viewStock(String productId) {
        ViewStockRequest request = new ViewStockRequest(productId);
        ViewStockCommand command = new ViewStockCommand(productRepository);
        return commandExecutor.execute(command, request);
    }

    public AdjustStockResponse adjustStock(
            String productId,
            int stockChange
    ) {
        AdjustStockRequest request = new AdjustStockRequest(productId, stockChange);
        AdjustStockCommand command = new AdjustStockCommand(productRepository);
        return commandExecutor.execute(command, request);
    }

    public GenerateSalesReportResponse getSalesReport(YearMonth yearMonth) {
        GenerateSalesReportRequest request = new GenerateSalesReportRequest(yearMonth);
        GenerateSalesReportCommand command = new GenerateSalesReportCommand(orderClient);
        return commandExecutor.execute(command, request);
    }

    public AddReviewResponse addReview(
            String productId,
            String userId,
            int rating,
            String comment
    ) {
        AddReviewRequest request = new AddReviewRequest(productId, userId, rating, comment);
        AddReviewCommand command = new AddReviewCommand(productRepository);
        return commandExecutor.execute(command, request);
    }
}