package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class ChiTietHoaDon implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID uuid = UUID.randomUUID();
    private HoaDon hoaDon;
    private Thuoc_SP_TheoLo loHang;
    private int soLuong = 1;
    private DonViTinh dvt;
    private double donGia;
    private double giamGia;
    private ChiTietKhuyenMai ctKM;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon hoaDon, Thuoc_SP_TheoLo loHang, int soLuong, DonViTinh dvt, double donGia, double giamGia) {
        this.hoaDon = hoaDon;
        this.loHang = loHang;
        this.soLuong = soLuong;
        this.dvt = dvt;
        this.donGia = donGia;
        this.giamGia = giamGia;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Thuoc_SP_TheoLo getLoHang() {
        return loHang;
    }

    public void setLoHang(Thuoc_SP_TheoLo loHang) {
        this.loHang = loHang;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int v) {
        soLuong = v;
    }
    public DonViTinh getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinh dvt) {
        this.dvt = dvt;
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
    public ChiTietKhuyenMai getChiTietKhuyenMai() {
        return ctKM;
    }
    public void setChiTietKhuyenMai(ChiTietKhuyenMai ctKm) {
        this.ctKM = ctKm;
    }


    public double tinhThanhTien() {
        double thanhTien = getSoLuong() * getDonGia() - getGiamGia();
        return thanhTien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChiTietHoaDon)) return false;
        ChiTietHoaDon other = (ChiTietHoaDon) o;
        return uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "hoaDon=" + hoaDon +
                ", loHang=" + loHang +
                ", dvt=" + dvt +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", giamGia=" + giamGia +
                '}';
    }
}
