package com.example.usescase.product;

import com.example.models.Product;
import com.example.usescase.exception.ProductMandatoryFieldException;
import com.example.usescase.exception.ProductNotFoundException;
import com.example.port.ProductRepository;

public class DeleteProduct{
    private ProductRepository productRepository;

    public DeleteProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void process(Product product) {
        if (product.getId() == null) {
            throw new ProductMandatoryFieldException("Id");
        } else if (!productRepository.findById(product.getId()).isPresent()) {
            throw new ProductNotFoundException(product.getId());
        }
        productRepository.delete(product);
    }
}
