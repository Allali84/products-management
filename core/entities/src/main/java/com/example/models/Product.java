package com.example.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {

    private Integer id;
    private Instant createDate;
    private String label;
    private Double unitPrice;
    private Double weight;
    private Double height;

    private List<Merchant> merchants;

    public List<Merchant> getMerchants() {
        if (merchants == null) return new ArrayList<>();
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
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
