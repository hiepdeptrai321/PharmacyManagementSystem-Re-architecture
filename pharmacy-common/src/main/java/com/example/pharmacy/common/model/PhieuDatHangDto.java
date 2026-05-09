package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class PhieuDatHangDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maPDat;
    private Timestamp ngayLap;
    private double soTienCoc;
    private String ghiChu;
    private KhachHangDto khachHang;
    private NhanVienDto nhanVien;
    private int trangthai;

    public PhieuDatHangDto() {
    }

    public PhieuDatHangDto(String maPDat, Timestamp ngayLap, double soTienCoc, String ghiChu, KhachHangDto khachHang) {
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

    public KhachHangDto getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHangDto khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVienDto getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVienDto nhanVien) {
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
        PhieuDatHangDto that = (PhieuDatHangDto) o;
        return Objects.equals(maPDat, that.maPDat);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPDat);
    }

    @Override
    public String toString() {
        return "PhieuDatHangDto{" +
                "maPDat='" + maPDat + '\'' +
                ", ngayLap=" + ngayLap +
                ", soTienCoc=" + soTienCoc +
                ", ghiChu='" + ghiChu + '\'' +
                ", khachHang=" + khachHang +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
