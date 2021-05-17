package com.example.spring.mapper;

import com.example.models.Address;

public class AddressMapper {

    public static com.example.gs_producing_web_service.Address asAddressDto(Address in) {
        com.example.gs_producing_web_service.Address addressDto = new com.example.gs_producing_web_service.Address();

        addressDto.setNumber(in.getNumber());
        addressDto.setStreet(in.getStreet());
        addressDto.setZipcode(in.getZipcode());

        return addressDto;
    }

    public static Address asAddressEntity(com.example.gs_producing_web_service.Address in) {
        Address addressEntity = new Address();

        addressEntity.setNumber(in.getNumber());
        addressEntity.setStreet(in.getStreet());
        addressEntity.setZipcode(in.getZipcode());

        return addressEntity;
    }
}
