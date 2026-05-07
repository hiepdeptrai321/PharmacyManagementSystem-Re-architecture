package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NhomDuocLy")
public class NhomDuocLyEntity {
    @Id
    @Column(name = "MaNDL", nullable = false, length = 10)
    private String maNDL;

    @Column(name = "TenNDL", length = 50)
    private String tenNDL;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    public String getMaNDL() {
        return maNDL;
    }

    public void setMaNDL(String maNDL) {
        this.maNDL = maNDL;
    }

    public String getTenNDL() {
        return tenNDL;
    }

    public void setTenNDL(String tenNDL) {
        this.tenNDL = tenNDL;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
