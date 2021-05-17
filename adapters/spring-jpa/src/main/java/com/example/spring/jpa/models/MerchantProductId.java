package com.example.spring.jpa.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MerchantProductId implements Serializable {

    @Column(name = "merchant_id")
    private Integer merchantId;
    @Column(name = "product_id")
    private Integer productId;

    public MerchantProductId() {
    }

    public MerchantProductId(Integer merchantId, Integer productId) {
        super();
        this.merchantId = merchantId;
        this.productId = productId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantProductId that = (MerchantProductId) o;
        return Objects.equals(merchantId, that.merchantId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantId, productId);
    }
}
