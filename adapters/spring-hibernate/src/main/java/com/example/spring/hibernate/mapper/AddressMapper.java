package com.example.spring.hibernate.mapper;

import com.example.models.Address;
import com.example.spring.hibernate.models.AddressHibernate;
import com.example.spring.hibernate.models.MerchantHibernate;

public class AddressMapper {

    public static Address asAddressDto(AddressHibernate in) {
        Address addressDto = new Address();

        addressDto.setId(in.getId());
        addressDto.setNumber(in.getNumber());
        addressDto.setStreet(in.getStreet());
        addressDto.setZipcode(in.getZipcode());

        return addressDto;
    }

    public static AddressHibernate asAddressEntity(Address in, MerchantHibernate merchantEntity) {
        AddressHibernate addressEntity = new AddressHibernate();

        addressEntity.setId(in.getId());
        addressEntity.setNumber(in.getNumber());
        addressEntity.setStreet(in.getStreet());
        addressEntity.setZipcode(in.getZipcode());
        addressEntity.setMerchant(merchantEntity);

        return addressEntity;
    }
}
