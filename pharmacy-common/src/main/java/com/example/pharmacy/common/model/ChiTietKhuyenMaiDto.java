package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietKhuyenMaiDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SanPhamDto thuoc;
    private KhuyenMaiDto khuyenMai;
    private int slApDung;
    private int soHDToiDa;


    public ChiTietKhuyenMaiDto() {
    }
    public ChiTietKhuyenMaiDto(Thuoc_SanPhamDto thuoc, KhuyenMaiDto khuyenMai, int slApDung, int soHDToiDa) {
        this.thuoc = thuoc;
        this.khuyenMai = khuyenMai;
        this.slApDung = slApDung;
        this.soHDToiDa = soHDToiDa;
    }

    public Thuoc_SanPhamDto getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPhamDto thuoc) {
        this.thuoc = thuoc;
    }

    public KhuyenMaiDto getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMaiDto khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public int getSlApDung() {
        return slApDung;
    }

    public void setSlApDung(int slApDung) {
        this.slApDung = slApDung;
    }

    public int getSoHDToiDa() {
        return soHDToiDa;
    }

    public void setSoHDToiDa(int soHDToiDa) {
        this.soHDToiDa = soHDToiDa;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietKhuyenMaiDto that = (ChiTietKhuyenMaiDto) o;
        return Objects.equals(thuoc, that.thuoc) && Objects.equals(khuyenMai, that.khuyenMai);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thuoc, khuyenMai);
    }

    @Override
    public String toString() {
        return "ChiTietKhuyenMaiDto{" +
                "thuoc=" + thuoc +
                ", khuyenMai=" + khuyenMai +
                ", slApDung=" + slApDung +
                ", slToiDa=" + soHDToiDa +
                '}';
    }
}
