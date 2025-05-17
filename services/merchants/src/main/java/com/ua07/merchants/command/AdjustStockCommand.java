package com.ua07.merchants.command;

import com.ua07.merchants.dto.AdjustStockRequest;
import com.ua07.merchants.dto.AdjustStockResponse;
import com.ua07.merchants.enums.Category;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.producer.MerchantQueueProducer;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.Command;
import com.ua07.shared.enums.Role;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public class AdjustStockCommand implements Command<AdjustStockRequest, AdjustStockResponse> {

    private final ProductRepository productRepository;
    private final MerchantQueueProducer merchantQueueProducer;
    public AdjustStockCommand(ProductRepository productRepository,
                              MerchantQueueProducer merchantQueueProducer) {
        this.productRepository = productRepository;
        this.merchantQueueProducer = merchantQueueProducer;
    }

    @Override
    public AdjustStockResponse execute(AdjustStockRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (request.getRole() != Role.MERCHANT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User role is not Merchant");
            }

            if (request.getUserId() != product.getMerchantId()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not the owner merchant of the product");
            }

            int newStock = product.getStock() + request.getStockChange();
            if (newStock < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resulting stock cannot be negative");
            };

            Product updated;
            if(product.getCategory() == Category.LAPTOPS){
                updated = new Product.Builder()
                        .withId(product.getId())
                        .withMerchantId(product.getMerchantId())
                        .withName(product.getName())
                        .withDescription(product.getDescription())
                        .withPrice(product.getPrice())
                        .withStock(newStock)
                        .withCategory(product.getCategory())
                        .withCreatedAt(product.getCreatedAt())
                        .withReviews(product.getReviews())
                        .withAverageRating(product.getAverageRating())
                        .withProcessor(product.getProcessor())
                        .withRam(product.getRam())
                        .withStorage(product.getStorage())
                        .withMerchantId(product.getMerchantId())
                        .build();
            } else if (product.getCategory() == Category.BOOKS) {
                updated = new Product.Builder()
                        .withId(product.getId())
                        .withMerchantId(product.getMerchantId())
                        .withName(product.getName())
                        .withDescription(product.getDescription())
                        .withPrice(product.getPrice())
                        .withStock(newStock)
                        .withCategory(product.getCategory())
                        .withCreatedAt(product.getCreatedAt())
                        .withReviews(product.getReviews())
                        .withAverageRating(product.getAverageRating())
                        .withAuthor(product.getAuthor())
                        .withGenre(product.getGenre())
                        .withPages(product.getPages())
                        .withMerchantId(product.getMerchantId())
                        .build();
            }
            else if (product.getCategory() == Category.JACKETS){
                updated = new Product.Builder()
                        .withId(product.getId())
                        .withMerchantId(product.getMerchantId())
                        .withName(product.getName())
                        .withDescription(product.getDescription())
                        .withPrice(product.getPrice())
                        .withStock(newStock)
                        .withCategory(product.getCategory())
                        .withCreatedAt(product.getCreatedAt())
                        .withReviews(product.getReviews())
                        .withAverageRating(product.getAverageRating())
                        .withSize(product.getSize())
                        .withMaterial(product.getMaterial())
                        .withColor(product.getColor())
                        .withMerchantId(product.getMerchantId())
                        .build();
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported product category");
            }

            productRepository.save(updated);
            
            merchantQueueProducer.notifyProductShortage(updated.getId(), updated.getStock());
            return new AdjustStockResponse(updated);
        } else {
            throw new RuntimeException("Product not found with ID: " + request.getProductId());
        }
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo not supported for AdjustCommand");
    }
}
