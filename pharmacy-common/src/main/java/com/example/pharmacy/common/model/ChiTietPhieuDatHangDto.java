package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietPhieuDatHangDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private PhieuDatHangDto phieuDatHang;
    private Thuoc_SanPhamDto thuoc;
    private int soLuong;
    private double donGia;
    private double giamGia;
    private String dvt;
    private boolean trangThai;

    public ChiTietPhieuDatHangDto() {
    }
    public ChiTietPhieuDatHangDto(PhieuDatHangDto phieuDatHang, Thuoc_SanPhamDto thuoc, int soLuong, double donGia, double giamGia, String dvt) {
        this.phieuDatHang = phieuDatHang;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giamGia = giamGia;
    }

    public PhieuDatHangDto getPhieuDatHang() {
        return phieuDatHang;
    }

    public void setPhieuDatHang(PhieuDatHangDto phieuDatHang) {
        this.phieuDatHang = phieuDatHang;
    }

    public Thuoc_SanPhamDto getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPhamDto thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuDatHangDto that = (ChiTietPhieuDatHangDto) o;
        return Objects.equals(phieuDatHang, that.phieuDatHang) && Objects.equals(thuoc, that.thuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuDatHang, thuoc);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDatHangDto{" +
                "phieuDatHang=" + phieuDatHang +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", giamGia=" + giamGia +
                '}';
    }
}
