package com.example.port;

import com.example.models.Product;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    void delete(Product product);
    Optional<Product> findById(Integer id);
    Product update(Product product);
    Optional<Product> findByLabel(String label);
}
