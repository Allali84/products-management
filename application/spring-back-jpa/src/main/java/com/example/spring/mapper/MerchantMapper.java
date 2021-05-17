package com.example.spring.mapper;

import com.example.gs_producing_web_service.Merchant;
import com.example.spring.exception.MappingException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.stream.Collectors;

public class MerchantMapper {

    public static Merchant asMerchantDto(com.example.models.Merchant in) {
        Merchant merchantDto = new Merchant();

        merchantDto.setId(in.getId() != null ? BigInteger.valueOf(in.getId()) : null);
        merchantDto.setName(in.getName());
        merchantDto.setLastName(in.getLastname());
        String dateTimeString = in.getCreateDate().toString();
        XMLGregorianCalendar date2
                = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        } catch (DatatypeConfigurationException e) {
            throw new MappingException(" create date of Merchant");
        }
        merchantDto.setCreateDate(date2);
        String i = in.getBirthdate().toInstant().toString();
        XMLGregorianCalendar date3
                = null;
        try {
            date3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(i);
        } catch (DatatypeConfigurationException e) {
            throw new MappingException(" birthdate of Merchant");
        }
        merchantDto.setBirthdate(date3);
        merchantDto.getAddresses().addAll(in.getAddresses().stream().map(AddressMapper::asAddressDto).collect(Collectors.toList()));
        merchantDto.getProducts().addAll(
                in.getProducts()
                        .stream()
                        .map(ProductMapper::asProductDto)
                        .collect(Collectors.toList())
        );

        return merchantDto;
    }

    public static com.example.models.Merchant asMerchantEntity(Merchant in) {
        com.example.models.Merchant merchantEntity = new com.example.models.Merchant();

        merchantEntity.setId(in.getId() != null ? in.getId().intValue() : null);
        merchantEntity.setName(in.getName());
        merchantEntity.setLastname(in.getLastName());
        merchantEntity.setCreateDate(in.getCreateDate().toGregorianCalendar().toInstant());
        merchantEntity.setBirthdate(in.getBirthdate().toGregorianCalendar().getTime());
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
    };
}
