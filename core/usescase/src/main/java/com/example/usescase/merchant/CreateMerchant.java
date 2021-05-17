package com.example.usescase.merchant;

import com.example.models.Merchant;
import com.example.usescase.exception.MerchantAlreadyExistsException;
import com.example.port.MerchantRepository;

public class CreateMerchant {
    private MerchantRepository merchantRepository;

    public CreateMerchant(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant process(Merchant merchant) {
        if (merchant.getId() != null && merchantRepository.findById(merchant.getId()).isPresent()) {
            throw new MerchantAlreadyExistsException(merchant.getId());
        }
        merchant.setId(null);
        return merchantRepository.save(merchant);
    }
}
