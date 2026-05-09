package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class ChiTietHoaDonDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID uuid = UUID.randomUUID();
    private HoaDonDto hoaDon;
    private Thuoc_SP_TheoLoDto loHang;
    private int soLuong = 1;
    private DonViTinhDto dvt;
    private double donGia;
    private double giamGia;
    private ChiTietKhuyenMaiDto ctKM;

    public ChiTietHoaDonDto() {
    }

    public ChiTietHoaDonDto(HoaDonDto hoaDon, Thuoc_SP_TheoLoDto loHang, int soLuong, DonViTinhDto dvt, double donGia, double giamGia) {
        this.hoaDon = hoaDon;
        this.loHang = loHang;
        this.soLuong = soLuong;
        this.dvt = dvt;
        this.donGia = donGia;
        this.giamGia = giamGia;
    }

    public HoaDonDto getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonDto hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Thuoc_SP_TheoLoDto getLoHang() {
        return loHang;
    }

    public void setLoHang(Thuoc_SP_TheoLoDto loHang) {
        this.loHang = loHang;
    }

    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int v) {
        soLuong = v;
    }
    public DonViTinhDto getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinhDto dvt) {
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
    public ChiTietKhuyenMaiDto getChiTietKhuyenMai() {
        return ctKM;
    }
    public void setChiTietKhuyenMai(ChiTietKhuyenMaiDto ctKm) {
        this.ctKM = ctKm;
    }


    public double tinhThanhTien() {
        double thanhTien = getSoLuong() * getDonGia() - getGiamGia();
        return thanhTien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChiTietHoaDonDto)) return false;
        ChiTietHoaDonDto other = (ChiTietHoaDonDto) o;
        return uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "ChiTietHoaDonDto{" +
                "hoaDon=" + hoaDon +
                ", loHang=" + loHang +
                ", dvt=" + dvt +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", giamGia=" + giamGia +
                '}';
    }
}
