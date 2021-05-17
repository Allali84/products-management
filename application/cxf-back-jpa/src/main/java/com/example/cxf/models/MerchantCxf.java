package com.example.cxf.models;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Merchant")
public class MerchantCxf {

    private Integer id;
    private XMLGregorianCalendar createDate;
    private String name;
    private String lastname;
    private Date birthdate;
    private List<ProductCxf> products;
    private List<AddressCxf> addresses;

    public List<ProductCxf> getProducts() {
        if (products == null) return new ArrayList<>();
        return products;
    }

    public void setProducts(List<ProductCxf> products) {
        this.products = products;
    }

    public List<AddressCxf> getAddresses() {
        if (addresses == null) return new ArrayList<>();
        return addresses;
    }

    public void setAddresses(List<AddressCxf> addresses) {
        this.addresses = addresses;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreateDate(XMLGregorianCalendar createDate) {
        this.createDate = createDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getId() {
        return id;
    }

    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
