package com.example.usescase.product;

import com.example.models.Product;
import com.example.usescase.exception.ProductAlreadyExistsException;
import com.example.port.ProductRepository;

public class CreateProduct {
    private ProductRepository productRepository;

    public CreateProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product process(Product product) {
        if (product.getId() != null && productRepository.findById(product.getId()).isPresent()) {
            throw new ProductAlreadyExistsException(product.getId());
        }
        product.setId(null);
        return productRepository.save(product);
    }
}
