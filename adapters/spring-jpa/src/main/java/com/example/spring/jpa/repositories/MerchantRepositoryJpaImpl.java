package com.example.spring.jpa.repositories;

import com.example.models.Merchant;
import com.example.models.Product;
import com.example.spring.jpa.mapper.MerchantMapper;
import com.example.spring.jpa.mapper.ProductMapper;
import com.example.spring.jpa.models.MerchantJpa;
import com.example.port.MerchantRepository;
import com.example.spring.jpa.models.MerchantProductJpa;
import com.example.spring.jpa.models.ProductJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MerchantRepositoryJpaImpl implements MerchantRepository {

    @Autowired
    private MerchantJpaRepository merchantJpaRepository;
    @Autowired
    private ProductJpaRepository productJpaRepository;
    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Override
    public Optional<Merchant> findByName(String name) {
        Optional<MerchantJpa> merchantJpa = merchantJpaRepository.findByName(name);
        return merchantJpa.map(MerchantMapper::asMerchantDto);
    }

    @Override
    public Optional<Merchant> findById(Integer id) {
        Optional<MerchantJpa> merchantJpa = merchantJpaRepository.findById(id);
        return merchantJpa.map(MerchantMapper::asMerchantDto);
    }

    @Override
    public Merchant save(Merchant merchant) {
        MerchantJpa merchantJpa = MerchantMapper.asMerchantEntity(merchant);
        merchantJpa.getAddresses().forEach(addressJpaRepository::save);
        return MerchantMapper.asMerchantDto(merchantJpaRepository.save(merchantJpa));
    }

    @Override
    public Merchant update(Merchant merchant) {
        return save(merchant);
    }

    @Override
    public Merchant associateProduct(Merchant merchant, Product product) {
        MerchantJpa merchantJpa = MerchantMapper.asMerchantEntity(merchant);
        ProductJpa productJpa = ProductMapper.asProductEntity(product);
        merchantJpa.associateProduct(productJpa);
        MerchantJpa merchantJpa1 = merchantJpaRepository.save(merchantJpa);
        return MerchantMapper.asMerchantDto(merchantJpa1);
    }

    @Override
    public void delete(Merchant merchant) {
        MerchantJpa merchantJpa = MerchantMapper.asMerchantEntity(merchant);
        merchantJpa.getAddresses().forEach(addressJpaRepository::delete);
        merchantJpaRepository.delete(merchantJpa);
    }

    public List<Merchant> findAll() {
        return merchantJpaRepository.findAll().stream().map(MerchantMapper::asMerchantDto).collect(Collectors.toList());
    }
}
