package com.ua07.merchants.command;

import com.ua07.merchants.dto.RecommendProductsRequest;
import com.ua07.merchants.dto.RecommendProductsResponse;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.Command;
import java.util.List;
import java.util.Optional;

public class RecommendProductsCommand
        implements Command<RecommendProductsRequest, RecommendProductsResponse> {

    private final ProductRepository productRepository;

    public RecommendProductsCommand(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public RecommendProductsResponse execute(RecommendProductsRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();
            String category = product.getCategory();

            List<Product> ProductsWithSameCategory = productRepository.findByCategory(category);

            List<Product> recommendations =
                    ProductsWithSameCategory.stream()
                            .filter(p -> !p.getId().equals(request.getProductId()))
                            .limit(10)
                            .toList();

            return new RecommendProductsResponse(recommendations);
        } else {
            throw new RuntimeException("Product not found with ID: " + request.getProductId());
        }
    }

    @Override
    public void undo() {
        // Implement undo logic if needed
        throw new UnsupportedOperationException("Undo not supported for RecommendProductsCommand");
    }
}
