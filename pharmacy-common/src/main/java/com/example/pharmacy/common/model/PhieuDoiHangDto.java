package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class PhieuDoiHangDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maPD;
    private NhanVienDto nhanVien;
    private KhachHangDto khachHang;
    private Timestamp ngayLap;
    private String ghiChu;
    private HoaDonDto hoaDon;

    public PhieuDoiHangDto() {
    }

    public PhieuDoiHangDto(String maPD, NhanVienDto nhanVien, KhachHangDto khachHang, Timestamp ngayLap, String ghiChu, HoaDonDto hoaDon) {
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
        PhieuDoiHangDto that = (PhieuDoiHangDto) o;
        return Objects.equals(maPD, that.maPD);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPD);
    }

    @Override
    public String toString() {
        return "PhieuDoiHangDto{" +
                "maPD='" + maPD + '\'' +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", ngayLap=" + ngayLap +
                ", ghiChu='" + ghiChu + '\'' +
                ", hoaDon=" + hoaDon +
                '}';
    }
}
