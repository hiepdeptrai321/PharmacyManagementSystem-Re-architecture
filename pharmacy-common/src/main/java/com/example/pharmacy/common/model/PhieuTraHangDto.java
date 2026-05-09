package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class PhieuTraHangDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maPT;
    private NhanVienDto nhanVien;
    private KhachHangDto khachHang;
    private Timestamp ngayLap;
    private String ghiChu;
    private HoaDonDto hoaDon;

    public PhieuTraHangDto() {
    }

    public PhieuTraHangDto(String maPT, NhanVienDto nhanVien, KhachHangDto khachHang, Timestamp ngayLap, String ghiChu, HoaDonDto hoaDon) {
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

    public NhanVienDto getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVienDto nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHangDto getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHangDto khachHang) {
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

    public HoaDonDto getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDonDto hoaDon) {
        this.hoaDon = hoaDon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PhieuTraHangDto that = (PhieuTraHangDto) o;
        return Objects.equals(maPT, that.maPT);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPT);
    }

    @Override
    public String toString() {
        return "PhieuTraHangDto{" +
                "maPT='" + maPT + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", ngayLap=" + ngayLap +
                ", ghiChu='" + ghiChu + '\'' +
                ", hoaDon=" + hoaDon +
                '}';
    }
}
