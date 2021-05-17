package com.example.spring.jpa.models;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity(name = "PRODUCT")
public class ProductJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "create_date")
    private Instant createDate;
    private String label;
    @Column(name = "unit_price")
    private Double unitPrice;
    private Double weight;
    private Double height;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER, targetEntity = MerchantProductJpa.class)
    private Set<MerchantProductJpa> merchants;

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

    public Set<MerchantProductJpa> getMerchants() {
        if (merchants == null) return new HashSet<>();
        return merchants;
    }

    public void setMerchants(Set<MerchantProductJpa> merchants) {
        this.merchants = merchants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductJpa that = (ProductJpa) o;
        return Objects.equals(createDate, that.createDate) && Objects.equals(label, that.label) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(weight, that.weight) && Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createDate, label, unitPrice, weight, height);
    }
}
