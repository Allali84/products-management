package com.example.usescase;

import com.example.models.Address;
import com.example.models.Merchant;
import com.example.models.Product;
import com.example.port.MerchantRepository;
import com.example.port.ProductRepository;
import com.example.usescase.exception.*;
import com.example.usescase.merchant.AssociateWithProduct;
import com.example.usescase.merchant.CreateMerchant;
import com.example.usescase.merchant.DeleteMerchant;
import com.example.usescase.merchant.UpdateMerchant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MerchantUsesCaseTests {

    private final static Instant CREATE_DATE = Instant.now();
    private final static java.util.Date BIRTH_DATE = Date.valueOf("1986-02-24");

    @Mock
    MerchantRepository merchantRepository;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("Create Merchant with success Test ")
    void givenMerchant_whenSaveMerchant_thenSucceed() {
        // Given
        Merchant merchant = createMerchant(null);
        Mockito.when(merchantRepository.save(merchant)).thenReturn(createMerchant(1));
        CreateMerchant createMerchantUseCase = new CreateMerchant(merchantRepository);

        // When
        Merchant insertedMerchant = createMerchantUseCase.process(merchant);

        // Then
        Mockito.verify(merchantRepository).save(merchant);
        Assertions.assertNotNull(insertedMerchant.getId());
        Assertions.assertEquals(1, insertedMerchant.getId());
    }

    @Test
    @DisplayName("Create Merchant when not found with success Test ")
    void givenMerchantWithId_whenSaveMerchant_thenSucceed() {
        // Given
        Merchant merchantWithID = createMerchant(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.empty());
        merchantWithID.setId(null);
        Mockito.when(merchantRepository.save(merchantWithID)).thenReturn(createMerchant(1));
        CreateMerchant createMerchantUseCase = new CreateMerchant(merchantRepository);

        // When
        merchantWithID.setId(1);
        Merchant insertedMerchant = createMerchantUseCase.process(merchantWithID);

        // Then
        merchantWithID.setId(null);
        Mockito.verify(merchantRepository).save(merchantWithID);
        Assertions.assertNotNull(insertedMerchant.getId());
        Assertions.assertEquals(1, insertedMerchant.getId());
    }

    @Test
    @DisplayName("Create Merchant with error when already exist Test ")
    void givenExistingMerchant_whenSaveMerchant_thenError() {
        // Given
        Merchant merchant = createMerchant(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.of(merchant));
        CreateMerchant createMerchantUseCase = new CreateMerchant(merchantRepository);

        // When and Then
        assertThrows(MerchantAlreadyExistsException.class, () -> {createMerchantUseCase.process(merchant);});
    }

    @Test
    @DisplayName("Update Merchant with success Test ")
    void givenMerchant_whenUpdateMerchant_thenSucceed() {
        // Given
        Merchant merchant = createMerchant(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.of(merchant));
        Mockito.when(merchantRepository.update(merchant)).thenReturn(createMerchant(1));
        UpdateMerchant updateMerchantUseCase = new UpdateMerchant(merchantRepository);

        // When
        Merchant updatedMerchant = updateMerchantUseCase.process(merchant);

        // Then
        Mockito.verify(merchantRepository).update(merchant);
        Assertions.assertNotNull(updatedMerchant.getId());
        Assertions.assertEquals(1, updatedMerchant.getId());
    }

    @Test
    @DisplayName("Update Merchant without id Test ")
    void givenMerchantWithoutId_whenUpdateMerchant_thenError() {
        // Given
        Merchant merchant = createMerchant(null);
        UpdateMerchant updateMerchantUseCase = new UpdateMerchant(merchantRepository);

        // When and Then
        assertThrows(MerchantMandatoryFieldException.class, () -> {updateMerchantUseCase.process(merchant);});
    }

    @Test
    @DisplayName("Not existing Merchant can't be updated Test ")
    void givenNotExistingMerchant_whenUpdateMerchant_thenError() {
        // Given
        Merchant merchant = createMerchant(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.empty());
        UpdateMerchant updateMerchantUseCase = new UpdateMerchant(merchantRepository);

        // When and Then
        assertThrows(MerchantNotFoundException.class, () -> {updateMerchantUseCase.process(merchant);});
    }

    @Test
    @DisplayName("Delete Merchant with success Test ")
    void givenMerchant_whenDeleteMerchant_thenSucceed() {
        // Given
        Merchant merchant = createMerchant(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.of(merchant));
        Mockito.doNothing().when(merchantRepository).delete(merchant);
        DeleteMerchant deleteMerchantUseCase = new DeleteMerchant(merchantRepository);

        // When
        deleteMerchantUseCase.process(merchant);

        // Then
        Mockito.verify(merchantRepository).delete(merchant);
    }

    @Test
    @DisplayName("Delete Merchant without id Test ")
    void givenMerchantWithoutId_whenDeleteMerchant_thenError() {
        // Given
        Merchant merchant = createMerchant(null);
        DeleteMerchant deleteMerchantUseCase = new DeleteMerchant(merchantRepository);

        // When and Then
        assertThrows(MerchantMandatoryFieldException.class, () -> {deleteMerchantUseCase.process(merchant);});
    }

    @Test
    @DisplayName("Not existing Merchant can't be deleted Test ")
    void givenNotExistingMerchant_whenDeleteMerchant_thenError() {
        // Given
        Merchant merchant = createMerchant(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.empty());
        DeleteMerchant deleteMerchantUseCase = new DeleteMerchant(merchantRepository);

        // When and Then
        assertThrows(MerchantNotFoundException.class, () -> {deleteMerchantUseCase.process(merchant);});
    }

    @Test
    @DisplayName("Associate Merchant with Product with success Test ")
    void givenMerchantAndProduct_whenAssociated_thenSucceed() {
        // Given
        Merchant merchant = createMerchant(1);
        Product product = createProduct(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.of(merchant));
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Mockito.when(merchantRepository.associateProduct(merchant, product)).thenReturn(merchant);
        AssociateWithProduct associateWithProductUseCase = new AssociateWithProduct(merchantRepository, productRepository);

        // When
        Merchant associatedMerchant = associateWithProductUseCase.process(merchant, product);

        // Then
        Mockito.verify(merchantRepository).associateProduct(merchant, product);
        Assertions.assertNotNull(associatedMerchant.getId());
        Assertions.assertEquals(1, associatedMerchant.getId());
    }

    @Test
    @DisplayName("Associate Merchant without ID with Product with error Test ")
    void givenMerchantWithoutIdAndProduct_whenAssociated_thenError() {
        // Given
        Merchant merchant = createMerchant(null);
        Product product = createProduct(1);
        AssociateWithProduct associateWithProductUseCase = new AssociateWithProduct(merchantRepository, productRepository);

        // When and Then
        assertThrows(MerchantMandatoryFieldException.class, () -> {associateWithProductUseCase.process(merchant, product);});
    }

    @Test
    @DisplayName("Not existing Merchant can't be associated with Product Test ")
    void givenNotExistingMerchantAndProduct_whenAssociated_thenError() {
        // Given
        Merchant merchant = createMerchant(1);
        Product product = createProduct(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.empty());
        AssociateWithProduct associateWithProductUseCase = new AssociateWithProduct(merchantRepository, productRepository);

        // When and Then
        assertThrows(MerchantNotFoundException.class, () -> {associateWithProductUseCase.process(merchant, product);});
    }

    @Test
    @DisplayName("Associate Merchant with Product without ID with error Test ")
    void givenMerchantAndProductWithoutId_whenAssociated_thenError() {
        // Given
        Merchant merchant = createMerchant(1);
        Product product = createProduct(null);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.of(merchant));
        AssociateWithProduct associateWithProductUseCase = new AssociateWithProduct(merchantRepository, productRepository);

        // When and Then
        assertThrows(ProductMandatoryFieldException.class, () -> {associateWithProductUseCase.process(merchant, product);});
    }

    @Test
    @DisplayName("Merchant can't be associated with not existing Product Test ")
    void givenMerchantAndNotExistingProduct_whenAssociated_thenError() {
        // Given
        Merchant merchant = createMerchant(1);
        Product product = createProduct(1);
        Mockito.when(merchantRepository.findById(1)).thenReturn(Optional.of(merchant));
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());
        AssociateWithProduct associateWithProductUseCase = new AssociateWithProduct(merchantRepository, productRepository);

        // When and Then
        assertThrows(ProductNotFoundException.class, () -> {associateWithProductUseCase.process(merchant, product);});
    }

    private Merchant createMerchant(Integer id) {
        Merchant merchant = new Merchant();
        merchant.setId(id);
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

    private Product createProduct(Integer id) {
        Product product = new Product();
        product.setId(id);
        product.setLabel("Label");
        product.setCreateDate(CREATE_DATE);
        product.setWeight(12.0);
        product.setHeight(2.0);
        product.setUnitPrice(45.99);
        return product;
    }
}
