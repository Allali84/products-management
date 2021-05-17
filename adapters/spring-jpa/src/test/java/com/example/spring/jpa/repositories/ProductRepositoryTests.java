package com.example.spring.jpa.repositories;


import com.example.models.Product;
import com.example.spring.jpa.config.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductRepositoryTests {

    private final static Instant CREATE_DATE = Instant.now();
    private final static java.util.Date BIRTH_DATE = Date.valueOf("1986-02-24");

    @Autowired
    private ProductRepositoryJpaImpl productJpaRepository;

    @Test
    @DisplayName("Create Product Test ")
    public void givenProduct_whenSave_thenGetOk() {
        // GIVEN
        Product product = createProduct();

        // WHEN
        productJpaRepository.save(product);

        // THEN
        Optional<Product> product2 = productJpaRepository.findByLabel("Label");
        assertTrue(product2.isPresent());
        Product product1 = product2.get();
        assertEquals(1, product1.getId());
        assertEquals("Label", product1.getLabel());
        assertEquals(CREATE_DATE, product1.getCreateDate());
        assertEquals(2.0, product1.getHeight());
        assertEquals(12.0, product1.getWeight());
        assertEquals(45.99, product1.getUnitPrice());
    }

    @Test
    @DisplayName("Update Product Test ")
    public void givenProduct_whenUpdate_thenGetOk() {
        // GIVEN
        Product product = createProduct();
        productJpaRepository.save(product);

        // WHEN
        Optional<Product> product2 = productJpaRepository.findByLabel("Label");
        assertTrue(product2.isPresent());
        Product product1 = product2.get();
        product1.setLabel("Label2");
        productJpaRepository.update(product1);

        // THEN
        assertEquals(1, productJpaRepository.findAll().size());
        product2 = productJpaRepository.findByLabel("Label2");
        assertTrue(product2.isPresent());
        product1 = product2.get();
        assertEquals(1, product1.getId());
        assertEquals("Label2", product1.getLabel());
        assertEquals(CREATE_DATE, product1.getCreateDate());
        assertEquals(2.0, product1.getHeight());
        assertEquals(12.0, product1.getWeight());
        assertEquals(45.99, product1.getUnitPrice());
    }

    @Test
    @DisplayName("Delete Product Test ")
    public void givenMerchant_whenDelete_thenGetKo() {
        // GIVEN
        Product product = createProduct();
        productJpaRepository.save(product);

        // WHEN
        Optional<Product> product2 = productJpaRepository.findByLabel("Label");
        assertTrue(product2.isPresent());
        productJpaRepository.delete(product2.get());

        // THEN
        assertEquals(0, productJpaRepository.findAll().size());
        product2 = productJpaRepository.findByLabel("Label");
        assertFalse(product2.isPresent());
        assertThrows(NoSuchElementException.class, product2::get);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setLabel("Label");
        product.setCreateDate(CREATE_DATE);
        product.setWeight(12.0);
        product.setHeight(2.0);
        product.setUnitPrice(45.99);
        return product;
    }
}
