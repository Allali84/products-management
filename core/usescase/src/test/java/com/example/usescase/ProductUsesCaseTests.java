package com.example.usescase;

import com.example.models.Product;
import com.example.port.ProductRepository;
import com.example.usescase.exception.*;
import com.example.usescase.product.CreateProduct;
import com.example.usescase.product.DeleteProduct;
import com.example.usescase.product.UpdateProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductUsesCaseTests {

    private final static Instant CREATE_DATE = Instant.now();
    private final static java.util.Date BIRTH_DATE = Date.valueOf("1986-02-24");

    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("Create Product with success Test ")
    void givenProduct_whenSaveProduct_thenSucceed() {
        // Given
        Product product = createProduct(null);
        Mockito.when(productRepository.save(product)).thenReturn(createProduct(1));
        CreateProduct createProductUseCase = new CreateProduct(productRepository);

        // When
        Product insertedProduct = createProductUseCase.process(product);

        // Then
        Mockito.verify(productRepository).save(product);
        Assertions.assertNotNull(insertedProduct.getId());
        Assertions.assertEquals(1, insertedProduct.getId());
    }

    @Test
    @DisplayName("Create Product when not found with success Test ")
    void givenProductWithId_whenSaveProduct_thenSucceed() {
        // Given
        Product productWithID = createProduct(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());
        productWithID.setId(null);
        Mockito.when(productRepository.save(productWithID)).thenReturn(createProduct(1));
        CreateProduct createProductUseCase = new CreateProduct(productRepository);

        // When
        productWithID.setId(1);
        Product insertedProduct = createProductUseCase.process(productWithID);

        // Then
        productWithID.setId(null);
        Mockito.verify(productRepository).save(productWithID);
        Assertions.assertNotNull(insertedProduct.getId());
        Assertions.assertEquals(1, insertedProduct.getId());
    }

    @Test
    @DisplayName("Create Product with error when already exist Test ")
    void givenExistingProduct_whenSaveProduct_thenError() {
        // Given
        Product product = createProduct(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        CreateProduct createProductUseCase = new CreateProduct(productRepository);

        // When and Then
        assertThrows(ProductAlreadyExistsException.class, () -> {createProductUseCase.process(product);});
    }

    @Test
    @DisplayName("Update Product with success Test ")
    void givenProduct_whenUpdateProduct_thenSucceed() {
        // Given
        Product product = createProduct(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.update(product)).thenReturn(createProduct(1));
        UpdateProduct updateProductUseCase = new UpdateProduct(productRepository);

        // When
        Product updatedProduct = updateProductUseCase.process(product);

        // Then
        Mockito.verify(productRepository).update(product);
        Assertions.assertNotNull(updatedProduct.getId());
        Assertions.assertEquals(1, updatedProduct.getId());
    }

    @Test
    @DisplayName("Update Product without id Test ")
    void givenProductWithoutId_whenUpdateProduct_thenError() {
        // Given
        Product product = createProduct(null);
        UpdateProduct updateProductUseCase = new UpdateProduct(productRepository);

        // When and Then
        assertThrows(ProductMandatoryFieldException.class, () -> {updateProductUseCase.process(product);});
    }

    @Test
    @DisplayName("Not existing Product can't be updated Test ")
    void givenNotExistingProduct_whenUpdateProduct_thenError() {
        // Given
        Product product = createProduct(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());
        UpdateProduct updateProductUseCase = new UpdateProduct(productRepository);

        // When and Then
        assertThrows(ProductNotFoundException.class, () -> {updateProductUseCase.process(product);});
    }

    @Test
    @DisplayName("Delete Product with success Test ")
    void givenProduct_whenDeleteProduct_thenSucceed() {
        // Given
        Product product = createProduct(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Mockito.doNothing().when(productRepository).delete(product);
        DeleteProduct deleteProductUseCase = new DeleteProduct(productRepository);

        // When
        deleteProductUseCase.process(product);

        // Then
        Mockito.verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("Delete Product without id Test ")
    void givenProductWithoutId_whenDeleteProduct_thenError() {
        // Given
        Product product = createProduct(null);
        DeleteProduct deleteProductUseCase = new DeleteProduct(productRepository);

        // When and Then
        assertThrows(ProductMandatoryFieldException.class, () -> {deleteProductUseCase.process(product);});
    }

    @Test
    @DisplayName("Not existing Product can't be deleted Test ")
    void givenNotExistingProduct_whenDeleteProduct_thenError() {
        // Given
        Product product = createProduct(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());
        DeleteProduct deleteProductUseCase = new DeleteProduct(productRepository);

        // When and Then
        assertThrows(ProductNotFoundException.class, () -> {deleteProductUseCase.process(product);});
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
