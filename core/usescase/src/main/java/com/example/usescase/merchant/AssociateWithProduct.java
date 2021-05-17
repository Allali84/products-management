package com.example.usescase.merchant;

import com.example.models.Merchant;
import com.example.models.Product;
import com.example.port.MerchantRepository;
import com.example.port.ProductRepository;
import com.example.usescase.exception.MerchantMandatoryFieldException;
import com.example.usescase.exception.MerchantNotFoundException;
import com.example.usescase.exception.ProductMandatoryFieldException;
import com.example.usescase.exception.ProductNotFoundException;

public class AssociateWithProduct {
    private MerchantRepository merchantRepository;
    private ProductRepository productRepository;

    public AssociateWithProduct(MerchantRepository merchantRepository, ProductRepository productRepository) {
        this.merchantRepository = merchantRepository;
        this.productRepository = productRepository;
    }

    public Merchant process(Merchant merchant, Product product) {
        if (merchant.getId() == null) {
            throw new MerchantMandatoryFieldException("Id");
        } else if (!merchantRepository.findById(merchant.getId()).isPresent()) {
            throw new MerchantNotFoundException(merchant.getId());
        }
        if (product.getId() == null) {
            throw new ProductMandatoryFieldException("Id");
        } else if (!productRepository.findById(product.getId()).isPresent()) {
            throw new ProductNotFoundException(product.getId());
        }

        return merchantRepository.associateProduct(merchant, product);
    }
}
