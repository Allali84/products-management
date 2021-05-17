package com.example.usescase.product;

import com.example.models.Product;
import com.example.usescase.exception.ProductMandatoryFieldException;
import com.example.usescase.exception.ProductNotFoundException;
import com.example.port.ProductRepository;

public class UpdateProduct{
    private ProductRepository productRepository;

    public UpdateProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product process(Product product) {
        if (product.getId() == null) {
            throw new ProductMandatoryFieldException("Id");
        } else if (!productRepository.findById(product.getId()).isPresent()) {
            throw new ProductNotFoundException(product.getId());
        }
        return productRepository.update(product);
    }
}
