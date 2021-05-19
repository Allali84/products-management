package com.example.spring.hibernate.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "MERCHANT_PRODUCT")
public class MerchantProductJpa implements Serializable {

    @EmbeddedId
    private MerchantProductId id = new MerchantProductId();

    @ManyToOne
    @MapsId("merchantId")
    private MerchantHibernate merchant;
    @ManyToOne
    @MapsId("productId")
    private ProductHibernate product;

    @Column(name = "associated_date")
    private Date associatedDate = new Date();

    public MerchantProductJpa() {
    }

    public MerchantProductJpa(MerchantHibernate merchant, ProductHibernate product) {
        this.merchant = merchant;
        this.product = product;
    }

    public MerchantProductJpa(MerchantProductId id, MerchantHibernate merchant, ProductHibernate product) {
        this.id = id;
        this.merchant = merchant;
        this.product = product;
    }

    public MerchantProductId getId() {
        return id;
    }

    public void setId(MerchantProductId id) {
        this.id = id;
    }

    public MerchantHibernate getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantHibernate merchant) {
        this.merchant = merchant;
    }

    public ProductHibernate getProduct() {
        return product;
    }

    public void setProduct(ProductHibernate product) {
        this.product = product;
    }

    public Date getAssociatedDate() {
        return associatedDate;
    }

    public void setAssociatedDate(Date associatedDate) {
        this.associatedDate = associatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantProductJpa that = (MerchantProductJpa) o;
        return Objects.equals(id, that.id) && Objects.equals(merchant, that.merchant) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchant, product);
    }
}
