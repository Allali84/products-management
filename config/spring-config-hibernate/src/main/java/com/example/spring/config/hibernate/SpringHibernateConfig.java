package com.example.spring.config.hibernate;

import com.example.spring.hibernate.repositories.MerchantRepositoryHibernateImpl;
import com.example.spring.hibernate.repositories.ProductRepositoryHibernateImpl;
import com.example.usescase.merchant.*;
import com.example.usescase.product.CreateProduct;
import com.example.usescase.product.DeleteProduct;
import com.example.usescase.product.FindProductByLabel;
import com.example.usescase.product.UpdateProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringHibernateConfig {

    private MerchantRepositoryHibernateImpl merchantRepositoryHibernate;
    private ProductRepositoryHibernateImpl productRepositoryHibernate;


    @Autowired
    public SpringHibernateConfig(MerchantRepositoryHibernateImpl merchantRepositoryHibernate, ProductRepositoryHibernateImpl productRepositoryHibernate) {
        this.merchantRepositoryHibernate = merchantRepositoryHibernate;
        this.productRepositoryHibernate = productRepositoryHibernate;
    }

    public CreateMerchant createMerchant() {
        return new CreateMerchant(merchantRepositoryHibernate);
    }

    public UpdateMerchant updateMerchant() {
        return new UpdateMerchant(merchantRepositoryHibernate);
    }

    public DeleteMerchant deleteMerchant() {
        return new DeleteMerchant(merchantRepositoryHibernate);
    }

    public AssociateWithProduct associateWithProduct() {
        return new AssociateWithProduct(merchantRepositoryHibernate, productRepositoryHibernate);
    }

    public FindMerchantByName findMerchantByName() {
        return new FindMerchantByName(merchantRepositoryHibernate);
    }

    public FindProductByLabel findProductByLabel() {
        return new FindProductByLabel(productRepositoryHibernate);
    }

    public CreateProduct createProduct() {
        return new CreateProduct(productRepositoryHibernate);
    }

    public UpdateProduct updateProduct() {
        return new UpdateProduct(productRepositoryHibernate);
    }

    public DeleteProduct deleteProduct() {
        return new DeleteProduct(productRepositoryHibernate);
    }
}
