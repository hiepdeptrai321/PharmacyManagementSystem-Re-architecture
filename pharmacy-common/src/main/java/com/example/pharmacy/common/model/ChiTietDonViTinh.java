package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietDonViTinh implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SanPham thuoc;
    private DonViTinh dvt;
    private double heSoQuyDoi;
    private double giaNhap;
    private double giaBan;
    private boolean donViCoBan;

    public ChiTietDonViTinh() {
    }

    public ChiTietDonViTinh(Thuoc_SanPham thuoc, DonViTinh dvt, double heSoQuyDoi, double giaNhap, double giaBan, boolean donViCoBan) {
        this.thuoc = thuoc;
        this.dvt = dvt;
        this.heSoQuyDoi = heSoQuyDoi;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.donViCoBan = donViCoBan;
    }

    public Thuoc_SanPham getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPham thuoc) {
        this.thuoc = thuoc;
    }

    public DonViTinh getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinh dvt) {
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
        ChiTietDonViTinh that = (ChiTietDonViTinh) o;
        return Objects.equals(thuoc, that.thuoc) && Objects.equals(dvt, that.dvt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thuoc, dvt);
    }

    @Override
    public String toString() {
        return "ChiTietDonViTinh{" +
                "thuoc=" + thuoc +
                ", dvt=" + dvt +
                ", heSoQuyDoi=" + heSoQuyDoi +
                ", giaNhap=" + giaNhap +
                ", giaBan=" + giaBan +
                ", donViCoBan=" + donViCoBan +
                '}';
    }
}
