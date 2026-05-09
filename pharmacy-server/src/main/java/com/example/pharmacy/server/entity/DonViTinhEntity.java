package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DonViTinh")
public class DonViTinhEntity {
    @Id
    @Column(name = "MaDVT", nullable = false, length = 10)
    private String maDVT;

    @Column(name = "TenDonViTinh", nullable = false, length = 50)
    private String tenDonViTinh;

    @Column(name = "KiHieu", nullable = false, length = 10)
    private String kiHieu;

    public String getMaDVT() {
        return maDVT;
    }

    public void setMaDVT(String maDVT) {
        this.maDVT = maDVT;
    }

    public String getTenDonViTinh() {
        return tenDonViTinh;
    }

    public void setTenDonViTinh(String tenDonViTinh) {
        this.tenDonViTinh = tenDonViTinh;
    }

    public String getKiHieu() {
        return kiHieu;
    }

    public void setKiHieu(String kiHieu) {
        this.kiHieu = kiHieu;
    }
}
