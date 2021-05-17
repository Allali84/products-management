package com.example.cxf.models;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Product")
public class ProductCxf {

    private Integer id;
    private XMLGregorianCalendar createDate;
    private String label;
    private Double unitPrice;
    private Double weight;
    private Double height;

    private List<MerchantCxf> merchants;

    public List<MerchantCxf> getMerchants() {
        if (merchants == null) return new ArrayList<>();
        return merchants;
    }

    public void setMerchants(List<MerchantCxf> merchants) {
        this.merchants = merchants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(XMLGregorianCalendar createDate) {
        this.createDate = createDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
