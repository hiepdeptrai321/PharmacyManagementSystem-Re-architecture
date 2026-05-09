package com.example.pharmacy.common.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenuePointDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDate bucketDate;
    private BigDecimal revenue;

    public RevenuePointDTO() {
    }

    public RevenuePointDTO(LocalDate bucketDate, BigDecimal revenue) {
        this.bucketDate = bucketDate;
        this.revenue = revenue;
    }

    public LocalDate getBucketDate() {
        return bucketDate;
    }

    public void setBucketDate(LocalDate bucketDate) {
        this.bucketDate = bucketDate;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
