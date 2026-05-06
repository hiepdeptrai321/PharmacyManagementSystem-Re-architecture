package com.example.pharmacy.common.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class TopSellingProductDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String productId;
    private String productName;
    private long quantitySold;
    private BigDecimal revenue;

    public TopSellingProductDTO() {
    }

    public TopSellingProductDTO(String productId, String productName, long quantitySold, BigDecimal revenue) {
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.revenue = revenue;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(long quantitySold) {
        this.quantitySold = quantitySold;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
