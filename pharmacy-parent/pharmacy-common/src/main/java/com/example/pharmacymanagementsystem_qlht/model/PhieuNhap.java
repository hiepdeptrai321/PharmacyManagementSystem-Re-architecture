package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class PhieuNhap implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maPN;
    private NhaCungCap nhaCungCap;
    private LocalDate ngayNhap;
    private Boolean trangThai;
    private String ghiChu;
    private NhanVien nhanVien;
    private Double TongTien;

    public PhieuNhap() {

    }

    public PhieuNhap(String maPN, NhaCungCap nhaCungCap, LocalDate ngayNhap, Boolean trangThai, String ghiChu, NhanVien nhanVien) {
        this.maPN = maPN;
        this.nhaCungCap = nhaCungCap;
        this.ngayNhap = ngayNhap;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.nhanVien = nhanVien;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
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
        PhieuNhap phieuNhap = (PhieuNhap) o;
        return Objects.equals(maPN, phieuNhap.maPN);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maPN);
    }

    @Override
    public String toString() {
        return "PhieuNhap{" +
                "maPN='" + maPN + '\'' +
                ", nhaCungCap=" + nhaCungCap +
                ", ngayNhap=" + ngayNhap +
                ", trangThai=" + trangThai +
                ", ghiChu='" + ghiChu + '\'' +
                ", nhanVien=" + nhanVien +
                '}';
    }

    public double getTongTien() {
        return TongTien == null ? 0d : TongTien;
    }

    public void setTongTien(Double tongTien) {
        TongTien = tongTien;
    }
}
