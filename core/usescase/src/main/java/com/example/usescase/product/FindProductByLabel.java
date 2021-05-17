package com.example.usescase.product;

import com.example.models.Product;
import com.example.port.ProductRepository;
import com.example.usescase.exception.ProductLabelNotFoundException;
import com.example.usescase.exception.ProductMandatoryFieldException;

import java.util.Optional;

public class FindProductByLabel {

    private ProductRepository productRepository;

    public FindProductByLabel(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product process(String label) {
        if (label == null) {
            throw new ProductMandatoryFieldException("label");
        }
        Optional<Product> product = productRepository.findByLabel(label);
        if (!product.isPresent()) {
            throw new ProductLabelNotFoundException(label);
        }
        return product.get();
    }
}
