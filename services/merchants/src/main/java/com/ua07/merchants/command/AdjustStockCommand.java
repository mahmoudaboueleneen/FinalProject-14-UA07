package com.ua07.merchants.command;

import com.ua07.merchants.dto.AdjustStockRequest;
import com.ua07.merchants.dto.AdjustStockResponse;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.Command;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class AdjustStockCommand implements Command<AdjustStockRequest, AdjustStockResponse> {

    private final ProductRepository productRepository;

    private AdjustStockRequest RequestExecuted;

    public AdjustStockCommand(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public AdjustStockResponse execute(AdjustStockRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int newStock = product.getStock() + request.getStockChange();
            if (newStock < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resulting stock cannot be negative");
            };

            Product updated = Product.builder()
                    .withId(product.getId())
                    .withName(product.getName())
                    .withDescription(product.getDescription())
                    .withPrice(product.getPrice())
                    .withStock(newStock)
                    .withCategory(product.getCategory())
                    .withAdditionalAttributes(product.getAdditionalAttributes())
                    .build();

            productRepository.save(updated);
            RequestExecuted = request;
            return new AdjustStockResponse(updated);
        } else {
            throw new RuntimeException("Product not found with ID: " + request.getProductId());
        }
    }

    @Override
    public void undo() {
        if (RequestExecuted == null) {
            throw new IllegalStateException("No execution history found for undo");
        }

        Optional<Product> optionalProduct = productRepository.findById(RequestExecuted.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int revertedStockChange = -RequestExecuted.getStockChange();
            int newStock = product.getStock() + revertedStockChange;

            Product reverted = Product.builder()
                    .withId(product.getId())
                    .withName(product.getName())
                    .withDescription(product.getDescription())
                    .withPrice(product.getPrice())
                    .withStock(newStock)
                    .withCategory(product.getCategory())
                    .withAdditionalAttributes(product.getAdditionalAttributes())
                    .build();

            productRepository.save(reverted);
        } else {
            throw new RuntimeException("Product not found with ID: " + RequestExecuted.getProductId());
        }
    }

}
