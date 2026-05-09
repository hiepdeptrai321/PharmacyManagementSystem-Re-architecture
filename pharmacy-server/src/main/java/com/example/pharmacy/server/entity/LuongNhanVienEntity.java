package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "LuongNhanVien")
public class LuongNhanVienEntity {
    @Id
    @Column(name = "MaLNV", nullable = false, length = 10)
    private String maLNV;

    @Column(name = "TuNgay", nullable = false)
    private Date tuNgay;

    @Column(name = "DenNgay")
    private Date denNgay;

    @Column(name = "LuongCoBan", nullable = false, precision = 18, scale = 2)
    private BigDecimal luongCoBan;

    @Column(name = "PhuCap", nullable = false, precision = 18, scale = 2)
    private BigDecimal phuCap;

    @Column(name = "GhiChu", nullable = false, length = 255)
    private String ghiChu;

    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;

    public String getMaLNV() {
        return maLNV;
    }

    public void setMaLNV(String maLNV) {
        this.maLNV = maLNV;
    }

    public Date getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(Date tuNgay) {
        this.tuNgay = tuNgay;
    }

    public Date getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(Date denNgay) {
        this.denNgay = denNgay;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(BigDecimal luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public BigDecimal getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(BigDecimal phuCap) {
        this.phuCap = phuCap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
