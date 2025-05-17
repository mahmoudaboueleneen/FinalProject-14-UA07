package com.ua07.merchants.service;

import com.ua07.merchants.client.OrderClient;
import com.ua07.merchants.command.AddReviewCommand;
import com.ua07.merchants.command.AdjustStockCommand;
import com.ua07.merchants.command.GenerateSalesReportCommand;
import com.ua07.merchants.dto.*;
import com.ua07.merchants.enums.Category;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.producer.MerchantQueueProducer;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.CommandExecutor;
import com.ua07.shared.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

@Service
public class MerchantService {

    private final ProductRepository productRepository;
    private final CommandExecutor commandExecutor;
    private final OrderClient orderClient;

    private final MerchantQueueProducer merchantQueueProducer;
    @Autowired
    public MerchantService(ProductRepository productRepository, CommandExecutor commandExecutor, OrderClient orderClient,
                           MerchantQueueProducer merchantQueueProducer) {
        this.productRepository = productRepository;
        this.commandExecutor  = commandExecutor;
        this.orderClient = orderClient;
        this.merchantQueueProducer = merchantQueueProducer;
    }

    public Product createProductLaptop(UUID userId, Role role, Product product) {
        if (role != Role.MERCHANT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
        }

        Product createdProduct = new Product.Builder()
                .withId(UUID.randomUUID().toString())
                .withMerchantId(userId)
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
                .withMerchantId(product.getMerchantId())
                .build();

        return productRepository.save(createdProduct);
    }

    public Product createProductBook(UUID userId, Role role, Product product) {
        if (role != Role.MERCHANT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
        }

        Product createdProduct = new Product.Builder()
                .withId(UUID.randomUUID().toString())
                .withMerchantId(userId)
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
                .withMerchantId(product.getMerchantId())
                .build();

        return productRepository.save(createdProduct);
    }

    public Product createProductJacket(UUID userId, Role role, Product product) {
        if (role != Role.MERCHANT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
        }

        Product createdProduct = new Product.Builder()
                .withId(UUID.randomUUID().toString())
                .withMerchantId(userId)
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
                .withMerchantId(product.getMerchantId())
                .build();

        return productRepository.save(createdProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProductLaptop(String id, UUID userId, Role role, Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (role != Role.MERCHANT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
            }

            if (userId != product.getMerchantId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not the owner merchant of the product");
            }

            Product updatedProduct = new Product.Builder()
                    .withId(product.getId())
                    .withMerchantId(product.getMerchantId())
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
                    .withMerchantId(product.getMerchantId())
                    .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProductBook(String id, UUID userId, Role role, Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (role != Role.MERCHANT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
            }

            if (userId != product.getMerchantId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not the owner merchant of the product");
            }

            Product updatedProduct = new Product.Builder()
                    .withId(product.getId())
                    .withMerchantId(product.getMerchantId())
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
                    .withMerchantId(product.getMerchantId())
                    .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }
    }

    @CachePut(value = "product", key = "#id")
    public Product updateProductJacket(String id, UUID userId, Role role, Product updated) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (role != Role.MERCHANT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
            }

            if (userId != product.getMerchantId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not the owner merchant of the product");
            }

            Product updatedProduct = new Product.Builder()
                    .withId(product.getId())
                    .withMerchantId(product.getMerchantId())
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
                    .withMerchantId(product.getMerchantId())
                    .build();

            return productRepository.save(updatedProduct);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id);
        }
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(String id, UUID userId, Role role) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: " + id));

        if (role != Role.MERCHANT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
        }

        if (userId != product.getMerchantId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not the owner merchant of the product");
        }

        productRepository.deleteById(id);
    }

    public ViewStockResponse viewStock(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return new ViewStockResponse(product.getId(), product.getName(), product.getStock());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + productId);
        }
    }

    public AdjustStockResponse adjustStock(
            String productId,
            UUID userId,
            Role role,
            int stockChange
    ) {
        AdjustStockRequest request = new AdjustStockRequest(productId, userId, role, stockChange);
        AdjustStockCommand command = new AdjustStockCommand(productRepository,merchantQueueProducer);
        return commandExecutor.execute(command, request);
    }

    public GenerateSalesReportResponse getSalesReport(Role role, YearMonth yearMonth) {
        GenerateSalesReportRequest request = new GenerateSalesReportRequest(role, yearMonth);
        GenerateSalesReportCommand command = new GenerateSalesReportCommand(orderClient);
        return commandExecutor.execute(command, request);
    }

    public AddReviewResponse addReview(
            String productId,
            UUID userId,
            int rating,
            String comment
    ) {
        AddReviewRequest request = new AddReviewRequest(productId, userId, rating, comment);
        AddReviewCommand command = new AddReviewCommand(productRepository);
        return commandExecutor.execute(command, request);
    }
}