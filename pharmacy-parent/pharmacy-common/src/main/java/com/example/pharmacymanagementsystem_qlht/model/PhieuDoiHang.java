package com.example.pharmacymanagementsystem_qlht.model;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuDoiHang {
    private String maPD;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private Timestamp ngayLap;
    private String ghiChu;
    private HoaDon hoaDon;

    public PhieuDoiHang() {
    }

    public PhieuDoiHang(String maPD, NhanVien nhanVien, KhachHang khachHang, Timestamp ngayLap, String ghiChu, HoaDon hoaDon) {
        this.maPD = maPD;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ngayLap = ngayLap;
        this.ghiChu = ghiChu;
        this.hoaDon = hoaDon;
    }

    public String getMaPD() {
        return maPD;
    }

    public void setMaPD(String maPD) {
        this.maPD = maPD;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhieuDoiHang that = (PhieuDoiHang) o;
        return Objects.equals(maPD, that.maPD);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPD);
    }

    @Override
    public String toString() {
        return "PhieuDoiHang{" +
                "maPD='" + maPD + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", ngayLap=" + ngayLap +
                ", ghiChu='" + ghiChu + '\'' +
                ", hoaDon=" + hoaDon +
                '}';
    }
}
