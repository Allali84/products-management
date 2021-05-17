package com.example.spring.jpa.mapper;

import com.example.models.Address;
import com.example.spring.jpa.models.AddressJpa;
import com.example.spring.jpa.models.MerchantJpa;

public class AddressMapper {

    public static Address asAddressDto(AddressJpa in) {
        Address addressDto = new Address();

        addressDto.setId(in.getId());
        addressDto.setNumber(in.getNumber());
        addressDto.setStreet(in.getStreet());
        addressDto.setZipcode(in.getZipcode());

        return addressDto;
    }

    public static AddressJpa asAddressEntity(Address in, MerchantJpa merchantEntity) {
        AddressJpa addressEntity = new AddressJpa();

        addressEntity.setId(in.getId());
        addressEntity.setNumber(in.getNumber());
        addressEntity.setStreet(in.getStreet());
        addressEntity.setZipcode(in.getZipcode());
        addressEntity.setMerchant(merchantEntity);

        return addressEntity;
    }
}
