package com.example.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Merchant {

    private Integer id;
    private Instant createDate;
    private String name;
    private String lastname;
    private Date birthdate;
    private List<Product> products;
    private List<Address> addresses;

    public List<Product> getProducts() {
        if (products == null) return new ArrayList<>();
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Address> getAddresses() {
        if (addresses == null) return new ArrayList<>();
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreateDate(Instant createDate) {
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

    public Instant getCreateDate() {
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
