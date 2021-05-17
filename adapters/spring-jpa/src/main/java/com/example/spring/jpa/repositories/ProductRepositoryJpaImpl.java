package com.example.spring.jpa.repositories;

import com.example.models.Merchant;
import com.example.models.Product;
import com.example.port.ProductRepository;
import com.example.spring.jpa.mapper.MerchantMapper;
import com.example.spring.jpa.mapper.ProductMapper;
import com.example.spring.jpa.models.MerchantJpa;
import com.example.spring.jpa.models.ProductJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryJpaImpl implements ProductRepository {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        ProductJpa productJpa = ProductMapper.asProductEntity(product);
        return ProductMapper.asProductDto(productJpaRepository.save(productJpa));
    }

    @Override
    public void delete(Product product) {
        ProductJpa productJpa = ProductMapper.asProductEntity(product);
        productJpaRepository.delete(productJpa);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productJpaRepository.findById(id).map(ProductMapper::asProductDto);
    }

    @Override
    public Product update(Product product) {
        return save(product);
    }

    @Override
    public Optional<Product> findByLabel(String label) {
        return productJpaRepository.findByLabel(label).map(ProductMapper::asProductDto);
    }

    public List<Product> findAll() {
        return productJpaRepository.findAll().stream().map(ProductMapper::asProductDto).collect(Collectors.toList());
    }
}
