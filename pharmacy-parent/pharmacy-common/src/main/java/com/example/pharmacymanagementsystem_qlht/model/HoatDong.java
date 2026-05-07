package com.example.pharmacymanagementsystem_qlht.model;

import java.sql.Timestamp;
import java.util.Objects;

public class HoatDong {
    private String maHD;
    private String loaiHD;
    private String bang;
    private Timestamp thoiGian;
    private String noiDung;
    private NhanVien nhanVien;

    public HoatDong() {
    }

    public HoatDong(String maHD, String loaiHD, String bang, Timestamp thoiGian, String noiDung, NhanVien nhanVien) {
        this.maHD = maHD;
        this.loaiHD = loaiHD;
        this.bang = bang;
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
        this.nhanVien = nhanVien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getLoaiHD() {
        return loaiHD;
    }

    public void setLoaiHD(String loaiHD) {
        this.loaiHD = loaiHD;
    }

    public String getBang() {
        return bang;
    }

    public void setBang(String bang) {
        this.bang = bang;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HoatDong hoatDong = (HoatDong) o;
        return Objects.equals(maHD, hoatDong.maHD);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maHD);
    }

    @Override
    public String toString() {
        return "HoatDong{" +
                "maHD='" + maHD + '\'' +
                ", loaiHD='" + loaiHD + '\'' +
                ", bang='" + bang + '\'' +
                ", thoiGian=" + thoiGian +
                ", ghiChu='" + noiDung + '\'' +
                ", nhanVien=" + nhanVien +
                '}';
    }
}
