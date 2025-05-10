package com.ua07.search.specification;

import com.ua07.search.model.Product;

public interface ProductSpecification {
    boolean isSatisfiedBy(Product product);

    default ProductSpecification and(ProductSpecification other) {
        return product -> this.isSatisfiedBy(product) && other.isSatisfiedBy(product);
    }

    default ProductSpecification or(ProductSpecification other) {
        return product -> this.isSatisfiedBy(product) || other.isSatisfiedBy(product);
    }

    default ProductSpecification not() {
        return product -> !this.isSatisfiedBy(product);
    }
}
