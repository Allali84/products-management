package com.example.cxf.mapper;


import com.example.cxf.exception.MappingException;
import com.example.cxf.models.MerchantCxf;
import com.example.models.Merchant;
import com.example.spring.jpa.models.MerchantProductJpa;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.stream.Collectors;

public class MerchantMapper {

    public static MerchantCxf asMerchantDto(Merchant in) {
        MerchantCxf merchantDto = new MerchantCxf();

        merchantDto.setId(in.getId());
        merchantDto.setName(in.getName());
        merchantDto.setLastname(in.getLastname());
        String dateTimeString = in.getCreateDate().toString();
        XMLGregorianCalendar date2 = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        } catch (DatatypeConfigurationException e) {
            throw new MappingException(" create date of Merchant");
        }
        merchantDto.setCreateDate(date2);
        merchantDto.setBirthdate(in.getBirthdate());
        merchantDto.setAddresses(in.getAddresses().stream().map(AddressMapper::asAddressDto).collect(Collectors.toList()));
        merchantDto.setProducts(
                in.getProducts()
                        .stream()
                        .map(ProductMapper::asProductDto)
                        .collect(Collectors.toList())
        );

        return merchantDto;
    }

    public static Merchant asMerchantEntity(MerchantCxf in) {
        Merchant merchantEntity = new Merchant();

        merchantEntity.setId(in.getId());
        merchantEntity.setName(in.getName());
        merchantEntity.setLastname(in.getLastname());
        merchantEntity.setCreateDate(in.getCreateDate().toGregorianCalendar().toInstant());
        merchantEntity.setBirthdate(in.getBirthdate());
        merchantEntity.setAddresses(
                in.getAddresses()
                        .stream()
                        .map(AddressMapper::asAddressEntity)
                        .collect(Collectors.toList())
        );
        merchantEntity.setProducts(
                in.getProducts()
                        .stream()
                        .map(ProductMapper::asProductEntity)
                        .collect(Collectors.toList())
        );

        return merchantEntity;
    }
}
