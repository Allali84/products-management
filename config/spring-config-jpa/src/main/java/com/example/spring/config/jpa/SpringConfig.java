package com.example.spring.config.jpa;

import com.example.spring.jpa.repositories.MerchantRepositoryJpaImpl;
import com.example.spring.jpa.repositories.ProductRepositoryJpaImpl;
import com.example.usescase.merchant.*;
import com.example.usescase.product.CreateProduct;
import com.example.usescase.product.DeleteProduct;
import com.example.usescase.product.FindProductByLabel;
import com.example.usescase.product.UpdateProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringConfig {

    private MerchantRepositoryJpaImpl merchantRepositoryJpa;
    private ProductRepositoryJpaImpl productRepositoryJpa;


    @Autowired
    public SpringConfig(MerchantRepositoryJpaImpl merchantRepositoryJpa, ProductRepositoryJpaImpl productRepositoryJpa) {
        this.merchantRepositoryJpa = merchantRepositoryJpa;
        this.productRepositoryJpa = productRepositoryJpa;
    }

    public CreateMerchant createMerchant() {
        return new CreateMerchant(merchantRepositoryJpa);
    }

    public UpdateMerchant updateMerchant() {
        return new UpdateMerchant(merchantRepositoryJpa);
    }

    public DeleteMerchant deleteMerchant() {
        return new DeleteMerchant(merchantRepositoryJpa);
    }

    public AssociateWithProduct associateWithProduct() {
        return new AssociateWithProduct(merchantRepositoryJpa, productRepositoryJpa);
    }

    public FindMerchantByName findMerchantByName() {
        return new FindMerchantByName(merchantRepositoryJpa);
    }

    public FindProductByLabel findProductByLabel() {
        return new FindProductByLabel(productRepositoryJpa);
    }

    public CreateProduct createProduct() {
        return new CreateProduct(productRepositoryJpa);
    }

    public UpdateProduct updateProduct() {
        return new UpdateProduct(productRepositoryJpa);
    }

    public DeleteProduct deleteProduct() {
        return new DeleteProduct(productRepositoryJpa);
    }
}
