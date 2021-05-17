package com.example.spring.jpa.repositories;

import com.example.spring.jpa.models.AddressJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressJpa, Integer> {
}
