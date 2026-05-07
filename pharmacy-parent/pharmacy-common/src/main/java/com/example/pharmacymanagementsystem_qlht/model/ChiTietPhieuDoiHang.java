package com.example.pharmacymanagementsystem_qlht.model;

import java.util.Objects;

public class ChiTietPhieuDoiHang {
    private Thuoc_SP_TheoLo loHang;
    private PhieuDoiHang phieuDoiHang;
    private Thuoc_SanPham thuoc;
    private int soLuong;
    private DonViTinh dvt;
    private String lyDoDoi ;


    public ChiTietPhieuDoiHang() {
    }

    public ChiTietPhieuDoiHang(Thuoc_SP_TheoLo loHang, PhieuDoiHang phieuDoiHang, Thuoc_SanPham thuoc, int soLuong, DonViTinh dvt, String lyDoDoi) {
        this.loHang = loHang;
        this.phieuDoiHang = phieuDoiHang;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.dvt = dvt;
        this.lyDoDoi = lyDoDoi;
    }

    public Thuoc_SP_TheoLo getLoHang() {
        return loHang;
    }

    public void setLoHang(Thuoc_SP_TheoLo loHang) {
        this.loHang = loHang;
    }

    public PhieuDoiHang getPhieuDoiHang() {
        return phieuDoiHang;
    }

    public void setPhieuDoiHang(PhieuDoiHang phieuDoiHang) {
        this.phieuDoiHang = phieuDoiHang;
    }

    public Thuoc_SanPham getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPham thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public DonViTinh getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinh dvt) {
        this.dvt = dvt;
    }
    public String getLyDoDoi() {
        return lyDoDoi;
    }

    public void setLyDoDoi(String lyDoDoi) {
        this.lyDoDoi = lyDoDoi;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuDoiHang that = (ChiTietPhieuDoiHang) o;
        return Objects.equals(loHang, that.loHang) && Objects.equals(phieuDoiHang, that.phieuDoiHang) && Objects.equals(dvt, that.dvt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loHang, phieuDoiHang,dvt);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDoiHang{" +
                "loHang=" + loHang +
                ", phieuDoiHang=" + phieuDoiHang +
                ", dvt=" + dvt +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                " lyDoDoi='" + lyDoDoi +
                '}';
    }
}
