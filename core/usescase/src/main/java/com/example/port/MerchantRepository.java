package com.example.port;

import com.example.models.Merchant;
import com.example.models.Product;

import java.util.Optional;

public interface MerchantRepository {
    Optional<Merchant> findByName(String name);
    Optional<Merchant> findById(Integer id);
    Merchant save(Merchant merchant);
    Merchant update(Merchant merchant);
    Merchant associateProduct(Merchant merchant, Product product);
    void delete(Merchant merchant);
}
