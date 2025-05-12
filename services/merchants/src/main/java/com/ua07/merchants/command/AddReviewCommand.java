package com.ua07.merchants.command;

import com.ua07.merchants.dto.AddReviewRequest;
import com.ua07.merchants.dto.AddReviewResponse;
import com.ua07.merchants.model.Product;
import com.ua07.merchants.model.Review;
import com.ua07.merchants.repository.ProductRepository;
import com.ua07.shared.command.Command;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

public class AddReviewCommand implements Command<AddReviewRequest, AddReviewResponse> {

    private final ProductRepository productRepository;

    public AddReviewCommand(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public AddReviewResponse execute(AddReviewRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Review review = new Review(request.getUserId(), request.getRating(), request.getComment());

            if (product.getReviews() == null) {
                product.setReviews(new ArrayList<>());
            }

            product.getReviews().add(review);
            productRepository.save(product);

            return new AddReviewResponse(product);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + request.getProductId());
        }
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Undo not supported for AddReviewCommand");
    }
}
