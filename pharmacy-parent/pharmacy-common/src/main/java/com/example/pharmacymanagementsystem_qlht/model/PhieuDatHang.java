package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class PhieuDatHang implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maPDat;
    private Timestamp ngayLap;
    private double soTienCoc;
    private String ghiChu;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private int trangthai;

    public PhieuDatHang() {
    }

    public PhieuDatHang(String maPDat, Timestamp ngayLap, double soTienCoc, String ghiChu, KhachHang khachHang) {
        this.maPDat = maPDat;
        this.ngayLap = ngayLap;
        this.soTienCoc = soTienCoc;
        this.ghiChu = ghiChu;
        this.khachHang = khachHang;
    }

    public String getMaPDat() {
        return maPDat;
    }

    public void setMaPDat(String maPDat) {
        this.maPDat = maPDat;
    }

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getSoTienCoc() {
        return soTienCoc;
    }

    public void setSoTienCoc(double soTienCoc) {
        this.soTienCoc = soTienCoc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhieuDatHang that = (PhieuDatHang) o;
        return Objects.equals(maPDat, that.maPDat);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPDat);
    }

    @Override
    public String toString() {
        return "PhieuDatHang{" +
                "maPDat='" + maPDat + '\'' +
                ", ngayLap=" + ngayLap +
                ", soTienCoc=" + soTienCoc +
                ", ghiChu='" + ghiChu + '\'' +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
