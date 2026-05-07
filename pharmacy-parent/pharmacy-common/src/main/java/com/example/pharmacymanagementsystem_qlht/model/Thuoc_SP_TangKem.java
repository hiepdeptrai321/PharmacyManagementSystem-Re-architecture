package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Thuoc_SP_TangKem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SanPham ThuocTangKem;
    private KhuyenMai khuyenmai;
    private int soLuong;

    public Thuoc_SP_TangKem(){
    }

    public Thuoc_SP_TangKem(Thuoc_SanPham thuocTangKem, KhuyenMai khuyenmai, int soLuong) {
        ThuocTangKem = thuocTangKem;
        this.khuyenmai = khuyenmai;
        this.soLuong = soLuong;
    }

    public Thuoc_SanPham getThuocTangKem() {
        return ThuocTangKem;
    }

    public void setThuocTangKem(Thuoc_SanPham thuocTangKem) {
        ThuocTangKem = thuocTangKem;
    }

    public KhuyenMai getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(KhuyenMai khuyenmai) {
        this.khuyenmai = khuyenmai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Thuoc_SP_TangKem that = (Thuoc_SP_TangKem) o;
        return Objects.equals(ThuocTangKem, that.ThuocTangKem) && Objects.equals(khuyenmai, that.khuyenmai);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ThuocTangKem, khuyenmai);
    }

    @Override
    public String toString() {
        return "Thuoc_SP_TangKem{" +
                "ThuocTangKem=" + ThuocTangKem +
                ", khuyenmai=" + khuyenmai +
                ", soLuong=" + soLuong +
                '}';
    }
}
