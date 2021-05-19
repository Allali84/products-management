package com.example.spring.hibernate.repositories;

import com.example.models.Address;
import com.example.models.Merchant;
import com.example.models.Product;
import com.example.spring.back.hibernate.config.HibernateConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = { HibernateConfig.class },
        loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MerchantRepositoryTests {

    private final static Instant CREATE_DATE = Instant.now();
    private final static java.util.Date BIRTH_DATE = Date.valueOf("1986-02-24");

    @Autowired
    private MerchantRepositoryHibernateImpl merchantHibernateRepository;
    @Autowired
    private ProductRepositoryHibernateImpl productHibernateRepository;

    @Test
    @DisplayName("Create Merchant Test ")
    public void givenMerchant_whenSave_thenGetOk() {
        // GIVEN
        Merchant merchant = createMerchant();

        // WHEN
        merchantHibernateRepository.save(merchant);

        // THEN
        Optional<Merchant> merchant2 = merchantHibernateRepository.findByName("John");
        assertTrue(merchant2.isPresent());
        assertEquals(1, merchant2.get().getId());
        assertEquals("John", merchant2.get().getName());
        assertEquals("Smith", merchant2.get().getLastname());
        assertEquals(CREATE_DATE, merchant2.get().getCreateDate());
        assertEquals(BIRTH_DATE, merchant2.get().getBirthdate());

        assertEquals(1, merchant2.get().getAddresses().size());
        Address address = merchant2.get().getAddresses().get(0);
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(1, address.getId());
        assertEquals(12, address.getNumber());
        assertEquals("STREET", address.getStreet());
        assertEquals("ZIPCODE", address.getZipcode());
    }

    @Test
    @DisplayName("Update Merchant Test ")
    public void givenMerchant_whenUpdate_thenGetOk() {
        // GIVEN
        assertEquals(0, merchantHibernateRepository.findAll().size());
        Merchant merchant = createMerchant();
        merchantHibernateRepository.save(merchant);

        // WHEN
        Optional<Merchant> merchant2 = merchantHibernateRepository.findByName("John");
        assertTrue(merchant2.isPresent());
        Merchant merchant1 = merchant2.get();
        merchant1.setName("James");
        merchant1.getAddresses().get(0).setZipcode("ZIPCODE2");
        merchantHibernateRepository.update(merchant1);

        // THEN
        assertEquals(1, merchantHibernateRepository.findAll().size());
        merchant2 = merchantHibernateRepository.findByName("James");
        assertTrue(merchant2.isPresent());
        merchant1 = merchant2.get();
        assertEquals(1, merchant1.getId());
        assertEquals("James", merchant1.getName());
        assertEquals("Smith", merchant1.getLastname());
        assertEquals(CREATE_DATE, merchant1.getCreateDate());
        assertEquals(BIRTH_DATE, merchant1.getBirthdate());

        assertEquals(1, merchant1.getAddresses().size());
        Address address = merchant1.getAddresses().get(0);
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(1, address.getId());
        assertEquals(12, address.getNumber());
        assertEquals("STREET", address.getStreet());
        assertEquals("ZIPCODE2", address.getZipcode());
    }

    @Test
    @DisplayName("Delete Merchant Test ")
    public void givenMerchant_whenDelete_thenGetKo() {
        // GIVEN
        Merchant merchant = createMerchant();
        merchantHibernateRepository.save(merchant);

        // WHEN
        Optional<Merchant> merchant2 = merchantHibernateRepository.findByName("John");
        assertTrue(merchant2.isPresent());
        merchantHibernateRepository.delete(merchant2.get());

        // THEN
        merchant2 = merchantHibernateRepository.findByName("John");
        assertFalse(merchant2.isPresent());
        assertThrows(NoSuchElementException.class, merchant2::get);
    }

    @Test
    @DisplayName("Associate Merchant with Product Test ")
    public void givenMerchantAndProduct_whenAssociate_thenGetOk() {
        // GIVEN
        Merchant merchant = createMerchant();
        Product product = createProduct();
        merchantHibernateRepository.save(merchant);
        productHibernateRepository.save(product);

        // WHEN
        Optional<Merchant> merchant2 = merchantHibernateRepository.findByName("John");
        assertTrue(merchant2.isPresent());
        Optional<Product> product2 = productHibernateRepository.findByLabel("Label");
        assertTrue(product2.isPresent());
        merchantHibernateRepository.associateProduct(merchant2.get(), product2.get());

        // THEN
        Optional<Merchant> merchant3 = merchantHibernateRepository.findById(1);
        // Merchant
        assertTrue(merchant3.isPresent());
        assertEquals(1, merchant3.get().getId());
        assertEquals("John", merchant3.get().getName());
        assertEquals("Smith", merchant3.get().getLastname());
        assertEquals(CREATE_DATE, merchant3.get().getCreateDate());
        assertEquals(BIRTH_DATE, merchant3.get().getBirthdate());

        // Product
        assertEquals(1, merchant3.get().getProducts().size());
        Product product1 = merchant3.get().getProducts().get(0);
        assertNotNull(product1);
        assertNotNull(product1.getId());
        assertEquals(1, product1.getId());
        assertEquals("Label", product1.getLabel());
        assertEquals(CREATE_DATE, product1.getCreateDate());
        assertEquals(2.0, product1.getHeight());
        assertEquals(12.0, product1.getWeight());
        assertEquals(45.99, product1.getUnitPrice());

        // Address
        assertEquals(1, merchant3.get().getAddresses().size());
        Address address = merchant3.get().getAddresses().get(0);
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(1, address.getId());
        assertEquals(12, address.getNumber());
        assertEquals("STREET", address.getStreet());
        assertEquals("ZIPCODE", address.getZipcode());
    }

    private Merchant createMerchant() {
        Merchant merchant = new Merchant();
        merchant.setName("John");
        merchant.setLastname("Smith");
        merchant.setCreateDate(CREATE_DATE);
        merchant.setBirthdate(BIRTH_DATE);
        merchant.setAddresses(new ArrayList<>());
        merchant.getAddresses().add(createAddress(merchant));
        return merchant;
    }

    private Address createAddress(Merchant merchant) {
        Address address = new Address();
        address.setMerchant(merchant);
        address.setNumber(12);
        address.setStreet("STREET");
        address.setZipcode("ZIPCODE");
        return address;
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
