package com.example.spring.boot.mapper;

import com.example.models.Product;
import com.example.spring.boot.exception.MappingException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.stream.Collectors;

public class ProductMapper {

    public static com.example.gs_producing_web_service.Product asProductDto(Product in) {
        com.example.gs_producing_web_service.Product productDto = new com.example.gs_producing_web_service.Product();

        productDto.setId(in.getId() != null ? BigInteger.valueOf(in.getId()) : null);
        productDto.setLabel(in.getLabel());
        productDto.setHeight(in.getHeight());
        String dateTimeString = in.getCreateDate().toString();
        XMLGregorianCalendar date2 = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTimeString);
        } catch (DatatypeConfigurationException e) {
            throw new MappingException(" create date of Merchant");
        }
        productDto.setCreateDate(date2);
        productDto.setWeight(in.getHeight());
        productDto.setUnitPrice(in.getUnitPrice());

        return productDto;
    }

    public static Product asProductEntity(com.example.gs_producing_web_service.Product in) {
        Product productEntity = new Product();

        productEntity.setId(in.getId() != null ? in.getId().intValue() : null);
        productEntity.setLabel(in.getLabel());
        productEntity.setHeight(in.getHeight());
        productEntity.setCreateDate(in.getCreateDate().toGregorianCalendar().toInstant());
        productEntity.setWeight(in.getHeight());
        productEntity.setUnitPrice(in.getUnitPrice());
        productEntity.setMerchants(
                in.getMerchants()
                        .stream()
                        .map(MerchantMapper::asMerchantEntity)
                        .collect(Collectors.toList())
        );

        return productEntity;
    }
}
