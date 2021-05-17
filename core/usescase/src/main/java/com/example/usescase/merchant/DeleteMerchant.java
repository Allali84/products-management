package com.example.usescase.merchant;

import com.example.models.Merchant;
import com.example.usescase.exception.MerchantMandatoryFieldException;
import com.example.usescase.exception.MerchantNotFoundException;
import com.example.port.MerchantRepository;

public class DeleteMerchant {
    private MerchantRepository merchantRepository;

    public DeleteMerchant(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public void process(Merchant merchant) {
        if (merchant.getId() == null) {
            throw new MerchantMandatoryFieldException("Id");
        } else if (!merchantRepository.findById(merchant.getId()).isPresent()) {
            throw new MerchantNotFoundException(merchant.getId());
        }
        merchantRepository.delete(merchant);
    }
}
