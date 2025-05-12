package com.ua07.merchants.command;

import com.ua07.merchants.dto.AdjustStockRequest;
import com.ua07.merchants.dto.AdjustStockResponse;
import com.ua07.merchants.dto.ViewStockRequest;
import com.ua07.merchants.dto.ViewStockResponse;
import com.ua07.merchants.enums.Category;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.Command;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

public class ViewStockCommand implements Command<ViewStockRequest, ViewStockResponse> {

    private final ProductRepository productRepository;

    public ViewStockCommand(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ViewStockResponse execute(ViewStockRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return new ViewStockResponse(product.getName(), product.getStock());
        } else {
            throw new RuntimeException("Product not found with ID: " + request.getProductId());
        }
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo not supported for ViewStockCommand");
    }
}
