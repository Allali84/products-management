package com.example.usescase.merchant;

import com.example.models.Merchant;
import com.example.usescase.exception.MerchantMandatoryFieldException;
import com.example.usescase.exception.MerchantNotFoundException;
import com.example.port.MerchantRepository;

public class UpdateMerchant {
    private MerchantRepository merchantRepository;

    public UpdateMerchant(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant process(Merchant merchant) {
        if (merchant.getId() == null) {
            throw new MerchantMandatoryFieldException("Id");
        } else if (!merchantRepository.findById(merchant.getId()).isPresent()) {
            throw new MerchantNotFoundException(merchant.getId());
        }
        return merchantRepository.update(merchant);
    }
}
