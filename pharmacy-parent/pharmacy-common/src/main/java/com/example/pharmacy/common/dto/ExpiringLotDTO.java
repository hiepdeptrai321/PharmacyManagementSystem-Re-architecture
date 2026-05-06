package com.example.pharmacy.common.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class ExpiringLotDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String lotCode;
    private String productId;
    private String productName;
    private LocalDate expiryDate;
    private int quantityOnHand;
    private long daysToExpiry;

    public ExpiringLotDTO() {
    }

    public ExpiringLotDTO(
            String lotCode,
            String productId,
            String productName,
            LocalDate expiryDate,
            int quantityOnHand,
            long daysToExpiry
    ) {
        this.lotCode = lotCode;
        this.productId = productId;
        this.productName = productName;
        this.expiryDate = expiryDate;
        this.quantityOnHand = quantityOnHand;
        this.daysToExpiry = daysToExpiry;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
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

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public long getDaysToExpiry() {
        return daysToExpiry;
    }

    public void setDaysToExpiry(long daysToExpiry) {
        this.daysToExpiry = daysToExpiry;
    }
}
