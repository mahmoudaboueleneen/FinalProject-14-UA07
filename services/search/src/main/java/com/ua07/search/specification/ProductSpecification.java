package com.ua07.search.specification;
import com.ua07.merchants.model.Product;
import java.util.List;

public interface ProductSpecification {
    boolean isSatisfiedBy(Product product);

    List<Product> filter(List<Product> products);

}
