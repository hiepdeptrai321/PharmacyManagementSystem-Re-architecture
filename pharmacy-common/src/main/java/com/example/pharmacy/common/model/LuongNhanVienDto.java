package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class LuongNhanVienDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maLNV;
    private Date tuNgay;
    private Date denNgay;
    private double luongCoBan;
    private double phuCap;
    private String ghiChu;
    private NhanVienDto nhanVien;

    public LuongNhanVienDto() {
    }

    public LuongNhanVienDto(String maLNV, Date tuNgay, Date denNgay, double luongCoBan, double phuCap, String ghiChu, NhanVienDto nhanVien) {
        this.maLNV = maLNV;
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.luongCoBan = luongCoBan;
        this.phuCap = phuCap;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
    }

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

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(double phuCap) {
        this.phuCap = phuCap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhanVienDto getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVienDto nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LuongNhanVienDto that = (LuongNhanVienDto) o;
        return Objects.equals(maLNV, that.maLNV);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maLNV);
    }

    @Override
    public String toString() {
        return "LuongNhanVienDto{" +
                "maLNV='" + maLNV + '\'' +
                ", tuNgay=" + tuNgay +
                ", denNgay=" + denNgay +
                ", luongCoBan=" + luongCoBan +
                ", phuCap=" + phuCap +
                ", ghiChu='" + ghiChu + '\'' +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
