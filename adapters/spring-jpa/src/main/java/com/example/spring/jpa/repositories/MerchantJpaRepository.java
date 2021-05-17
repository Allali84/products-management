package com.example.spring.jpa.repositories;

import com.example.spring.jpa.models.MerchantJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantJpaRepository extends JpaRepository<MerchantJpa, Integer> {
    Optional<MerchantJpa> findByName(String name);
}
