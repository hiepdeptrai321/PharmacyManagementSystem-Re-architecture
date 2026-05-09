package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Thuoc_SP_TangKemDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SanPhamDto ThuocTangKem;
    private KhuyenMaiDto khuyenmai;
    private int soLuong;

    public Thuoc_SP_TangKemDto(){
    }

    public Thuoc_SP_TangKemDto(Thuoc_SanPhamDto thuocTangKem, KhuyenMaiDto khuyenmai, int soLuong) {
        ThuocTangKem = thuocTangKem;
        this.khuyenmai = khuyenmai;
        this.soLuong = soLuong;
    }

    public Thuoc_SanPhamDto getThuocTangKem() {
        return ThuocTangKem;
    }

    public void setThuocTangKem(Thuoc_SanPhamDto thuocTangKem) {
        ThuocTangKem = thuocTangKem;
    }

    public KhuyenMaiDto getKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(KhuyenMaiDto khuyenmai) {
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
        Thuoc_SP_TangKemDto that = (Thuoc_SP_TangKemDto) o;
        return Objects.equals(ThuocTangKem, that.ThuocTangKem) && Objects.equals(khuyenmai, that.khuyenmai);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ThuocTangKem, khuyenmai);
    }

    @Override
    public String toString() {
        return "Thuoc_SP_TangKemDto{" +
                "ThuocTangKem=" + ThuocTangKem +
                ", khuyenmai=" + khuyenmai +
                ", soLuong=" + soLuong +
                '}';
    }
}
