package com.example.spring.jpa.models;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity(name = "MERCHANT")
public class MerchantJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "create_date")
    private Instant createDate;
    private String name;
    private String lastname;
    private Date birthdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "merchant", fetch = FetchType.EAGER, targetEntity = MerchantProductJpa.class)
    private Set<MerchantProductJpa> products;
    @OneToMany(mappedBy="merchant", fetch = FetchType.EAGER)
    private Set<AddressJpa> addresses;

    public void associateProduct(ProductJpa product) {
        MerchantProductJpa merchantProduct = new MerchantProductJpa(this, product);
        getProducts().add(merchantProduct);
        product.getMerchants().add(merchantProduct);
    }


    public void removeProduct(ProductJpa product) {
        for (Iterator<MerchantProductJpa> iterator = getProducts().iterator();
             iterator.hasNext(); ) {
            MerchantProductJpa merchantProduct = iterator.next();

            if (merchantProduct.getMerchant().equals(this) &&
                    merchantProduct.getProduct().equals(product)) {
                iterator.remove();
                merchantProduct.getProduct().getMerchants().remove(merchantProduct);
                merchantProduct.setMerchant(null);
                merchantProduct.setProduct(null);
            }
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Set<MerchantProductJpa> getProducts() {
        if (products == null) return new HashSet<>();
        return products;
    }

    public void setProducts(Set<MerchantProductJpa> products) {
        this.products = products;
    }

    public Set<AddressJpa> getAddresses() {
        if (addresses == null) return new HashSet<>();
        return addresses;
    }

    public void setAddresses(Set<AddressJpa> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantJpa that = (MerchantJpa) o;
        return Objects.equals(createDate, that.createDate) && Objects.equals(name, that.name) && Objects.equals(lastname, that.lastname) && Objects.equals(birthdate, that.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, name, lastname, birthdate);
    }
}
