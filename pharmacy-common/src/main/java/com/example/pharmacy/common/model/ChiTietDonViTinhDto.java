package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietDonViTinhDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SanPhamDto thuoc;
    private DonViTinhDto dvt;
    private double heSoQuyDoi;
    private double giaNhap;
    private double giaBan;
    private boolean donViCoBan;

    public ChiTietDonViTinhDto() {
    }

    public ChiTietDonViTinhDto(Thuoc_SanPhamDto thuoc, DonViTinhDto dvt, double heSoQuyDoi, double giaNhap, double giaBan, boolean donViCoBan) {
        this.thuoc = thuoc;
        this.dvt = dvt;
        this.heSoQuyDoi = heSoQuyDoi;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.donViCoBan = donViCoBan;
    }

    public Thuoc_SanPhamDto getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPhamDto thuoc) {
        this.thuoc = thuoc;
    }

    public DonViTinhDto getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinhDto dvt) {
        this.dvt = dvt;
    }

    public double getHeSoQuyDoi() {
        return heSoQuyDoi;
    }

    public void setHeSoQuyDoi(double heSoQuyDoi) {
        this.heSoQuyDoi = heSoQuyDoi;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public boolean isDonViCoBan() {
        return donViCoBan;
    }

    public void setDonViCoBan(boolean donViCoBan) {
        this.donViCoBan = donViCoBan;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietDonViTinhDto that = (ChiTietDonViTinhDto) o;
        return Objects.equals(thuoc, that.thuoc) && Objects.equals(dvt, that.dvt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thuoc, dvt);
    }

    @Override
    public String toString() {
        return "ChiTietDonViTinhDto{" +
                "thuoc=" + thuoc +
                ", dvt=" + dvt +
                ", heSoQuyDoi=" + heSoQuyDoi +
                ", giaNhap=" + giaNhap +
                ", giaBan=" + giaBan +
                ", donViCoBan=" + donViCoBan +
                '}';
    }
}
