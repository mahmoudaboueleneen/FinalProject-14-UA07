package com.ua07.merchants.command;

import com.ua07.merchants.model.Product;
import com.ua07.merchants.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecommendProductsCommand implements Command {

    private final String productId;
    private final ProductRepository productRepository;

    public RecommendProductsCommand(String productId, ProductRepository productRepository) {
        this.productId = productId;
        this.productRepository = productRepository;
    }

    @Override
    public Object execute() {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            String category = product.getCategory();

            List<Product> ProductsWithSameCategory = productRepository.findByCategory(category);

            return ProductsWithSameCategory.stream()
                    .filter(p -> !p.getId().equals(productId))
                    .limit(10)
                    .collect(Collectors.toList());
        }
        else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }
}
