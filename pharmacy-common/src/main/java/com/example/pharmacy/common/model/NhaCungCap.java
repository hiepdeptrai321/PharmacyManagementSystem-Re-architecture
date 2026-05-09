package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class NhaCungCap implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String SDT;
    private String email;
    private String GPKD;
    private String ghiChu;
    private String tenCongTy;
    private String MSThue;

    public NhaCungCap() {
    }

    public NhaCungCap(String maNCC, String tenNCC, String diaChi, String SDT, String email, String GPKD, String ghiChu, String tenCongTy, String MSThue) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.email = email;
        this.GPKD = GPKD;
        this.ghiChu = ghiChu;
        this.tenCongTy = tenCongTy;
        this.MSThue = MSThue;
    }

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

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGPKD() {
        return GPKD;
    }

    public void setGPKD(String GPKD) {
        this.GPKD = GPKD;
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

    public String getMSThue() {
        return MSThue;
    }

    public void setMSThue(String MSThue) {
        this.MSThue = MSThue;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NhaCungCap that = (NhaCungCap) o;
        return Objects.equals(maNCC, that.maNCC);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maNCC);
    }

    @Override
    public String toString() {
        return "NhaCungCap{" +
                "maNCC='" + maNCC + '\'' +
                ", tenNCC='" + tenNCC + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", SDT='" + SDT + '\'' +
                ", email='" + email + '\'' +
                ", GPKD='" + GPKD + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", tenCongTy='" + tenCongTy + '\'' +
                ", MSThue='" + MSThue + '\'' +
                '}';
    }
}
