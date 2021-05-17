package com.example.cxf.mapper;

import com.example.cxf.exception.MappingException;
import com.example.cxf.models.ProductCxf;
import com.example.models.Product;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductCxf asProductDto(Product in) {
        ProductCxf productDto = new ProductCxf();

        productDto.setId(in.getId());
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

    public static Product asProductEntity(ProductCxf in) {
        Product productEntity = new Product();

        productEntity.setId(in.getId());
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
