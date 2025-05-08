package com.ua07.merchants.command;

import com.ua07.merchants.model.Product;
import com.ua07.merchants.repository.ProductRepository;

import java.util.Optional;

public class AdjustStockCommand implements Command {

    private final String productId;
    private final int stockChange;
    private final ProductRepository productRepository;

    public AdjustStockCommand(String productId, int stockChange, ProductRepository productRepository) {
        this.productId = productId;
        this.stockChange = stockChange;
        this.productRepository = productRepository;
    }

    @Override
    public Object execute() {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int newStock = product.getStock() + stockChange;
            if (newStock < 0) newStock = 0;

            Product updated = new Product.ProductBuilder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(newStock)
                    .category(product.getCategory())
                    .additionalAttributes(product.getAdditionalAttributes())
                    .build();

            return productRepository.save(updated);
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }
}
