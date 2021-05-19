package com.example.spring.hibernate.models;

import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Entity(name = "ADDRESS")
public class AddressHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int number;
    private String street;
    private String zipcode;
    @ManyToOne
    @JoinColumn(name="merchant_id", nullable=true)
    private MerchantHibernate merchant;

    public MerchantHibernate getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantHibernate merchant) {
        this.merchant = merchant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
