package com.example.spring.jpa.repositories;

import com.example.spring.jpa.models.ProductJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<ProductJpa, Integer> {
    Optional<ProductJpa> findByLabel(String label);
}
