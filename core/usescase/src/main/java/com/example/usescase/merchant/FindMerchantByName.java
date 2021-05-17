package com.example.usescase.merchant;

import com.example.models.Merchant;
import com.example.usescase.exception.MerchantMandatoryFieldException;
import com.example.usescase.exception.MerchantNameNotFoundException;
import com.example.port.MerchantRepository;

import java.util.Optional;

public class FindMerchantByName {

    private MerchantRepository merchantRepository;

    public FindMerchantByName(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant process(String name) {
        if (name == null) {
            throw new MerchantMandatoryFieldException("name");
        }
        Optional<Merchant> merchant1 = merchantRepository.findByName(name);
        if (!merchant1.isPresent()) {
            throw new MerchantNameNotFoundException(name);
        }
        return merchant1.get();
    }
}
