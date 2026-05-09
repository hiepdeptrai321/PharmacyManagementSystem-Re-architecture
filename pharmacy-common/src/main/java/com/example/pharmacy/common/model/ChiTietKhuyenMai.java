package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietKhuyenMai implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SanPham thuoc;
    private KhuyenMai khuyenMai;
    private int slApDung;
    private int soHDToiDa;


    public ChiTietKhuyenMai() {
    }
    public ChiTietKhuyenMai(Thuoc_SanPham thuoc, KhuyenMai khuyenMai, int slApDung, int soHDToiDa) {
        this.thuoc = thuoc;
        this.khuyenMai = khuyenMai;
        this.slApDung = slApDung;
        this.soHDToiDa = soHDToiDa;
    }

    public Thuoc_SanPham getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPham thuoc) {
        this.thuoc = thuoc;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
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
        ChiTietKhuyenMai that = (ChiTietKhuyenMai) o;
        return Objects.equals(thuoc, that.thuoc) && Objects.equals(khuyenMai, that.khuyenMai);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thuoc, khuyenMai);
    }

    @Override
    public String toString() {
        return "ChiTietKhuyenMai{" +
                "thuoc=" + thuoc +
                ", khuyenMai=" + khuyenMai +
                ", slApDung=" + slApDung +
                ", slToiDa=" + soHDToiDa +
                '}';
    }
}
