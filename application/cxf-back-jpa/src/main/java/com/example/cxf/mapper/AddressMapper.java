package com.example.cxf.mapper;


import com.example.cxf.models.AddressCxf;
import com.example.cxf.models.MerchantCxf;
import com.example.models.Address;
import com.example.models.Merchant;

public class AddressMapper {

    public static AddressCxf asAddressDto(Address in) {
        AddressCxf addressDto = new AddressCxf();

        addressDto.setNumber(in.getNumber());
        addressDto.setStreet(in.getStreet());
        addressDto.setZipcode(in.getZipcode());

        return addressDto;
    }

    public static Address asAddressEntity(AddressCxf in) {
        Address addressEntity = new Address();

        addressEntity.setNumber(in.getNumber());
        addressEntity.setStreet(in.getStreet());
        addressEntity.setZipcode(in.getZipcode());

        return addressEntity;
    }
}
