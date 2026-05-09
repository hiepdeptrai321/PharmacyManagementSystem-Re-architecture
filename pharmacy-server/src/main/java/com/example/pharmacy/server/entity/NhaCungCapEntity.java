package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NhaCungCap")
public class NhaCungCapEntity {
    @Id
    @Column(name = "MaNCC", nullable = false, length = 10)
    private String maNCC;

    @Column(name = "TenNCC", nullable = false, length = 100)
    private String tenNCC;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "SDT", nullable = false, length = 20)
    private String sdt;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "GPKD", length = 50)
    private String gpKD;

    @Column(name = "GhiChu", columnDefinition = "TEXT")
    private String ghiChu;

    @Column(name = "TenCongTy", length = 100)
    private String tenCongTy;

    @Column(name = "MSThue", length = 20)
    private String msThue;

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGpKD() {
        return gpKD;
    }

    public void setGpKD(String gpKD) {
        this.gpKD = gpKD;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }

    public String getMsThue() {
        return msThue;
    }

    public void setMsThue(String msThue) {
        this.msThue = msThue;
    }
}
