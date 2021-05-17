package com.example.spring.jpa.mapper;

import com.example.models.Merchant;
import com.example.spring.jpa.models.MerchantJpa;
import com.example.spring.jpa.models.MerchantProductJpa;

import java.util.stream.Collectors;

public class MerchantMapper {

    public static Merchant asMerchantDto(MerchantJpa in) {
        Merchant merchantDto = new Merchant();

        merchantDto.setId(in.getId());
        merchantDto.setName(in.getName());
        merchantDto.setLastname(in.getLastname());
        merchantDto.setCreateDate(in.getCreateDate());
        merchantDto.setBirthdate(in.getBirthdate());
        merchantDto.setAddresses(in.getAddresses().stream().map(AddressMapper::asAddressDto).collect(Collectors.toList()));
        merchantDto.setProducts(
                in.getProducts()
                        .stream()
                        .map(merchantProductJpa -> ProductMapper.asProductDto(merchantProductJpa.getProduct()))
                        .collect(Collectors.toList())
        );

        return merchantDto;
    }

    public static MerchantJpa asMerchantEntity(Merchant in) {
        MerchantJpa merchantEntity = new MerchantJpa();

        merchantEntity.setId(in.getId());
        merchantEntity.setName(in.getName());
        merchantEntity.setLastname(in.getLastname());
        merchantEntity.setCreateDate(in.getCreateDate());
        merchantEntity.setBirthdate(in.getBirthdate());
        merchantEntity.setAddresses(
                in.getAddresses()
                        .stream()
                        .map( address -> AddressMapper.asAddressEntity(address, merchantEntity))
                        .collect(Collectors.toSet())
        );
        merchantEntity.setProducts(
                in.getProducts()
                        .stream()
                        .map(product -> new MerchantProductJpa(merchantEntity, ProductMapper.asProductEntity(product)))
                        .collect(Collectors.toSet())
        );
        return merchantEntity;
    }
}
