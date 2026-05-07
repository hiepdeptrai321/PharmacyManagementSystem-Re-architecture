package com.example.pharmacymanagementsystem_qlht.model;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuTraHang {
    private String maPT;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private Timestamp ngayLap;
    private String ghiChu;
    private HoaDon hoaDon;

    public PhieuTraHang() {
    }

    public PhieuTraHang(String maPT, NhanVien nhanVien, KhachHang khachHang, Timestamp ngayLap, String ghiChu, HoaDon hoaDon) {
        this.maPT = maPT;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ngayLap = ngayLap;
        this.ghiChu = ghiChu;
        this.hoaDon = hoaDon;
    }

    public String getMaPT() {
        return maPT;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
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
        PhieuTraHang that = (PhieuTraHang) o;
        return Objects.equals(maPT, that.maPT);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPT);
    }

    @Override
    public String toString() {
        return "PhieuTraHang{" +
                "maPT='" + maPT + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", ngayLap=" + ngayLap +
                ", ghiChu='" + ghiChu + '\'' +
                ", hoaDon=" + hoaDon +
                '}';
    }
}
