package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class LoaiKhuyenMai implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maLoai;
    private String tenLoai;
    private String moTa;

    public LoaiKhuyenMai() {
    }

    public LoaiKhuyenMai(String maLoai, String tenLoai, String moTa) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoaiKhuyenMai that = (LoaiKhuyenMai) o;
        return Objects.equals(maLoai, that.maLoai);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maLoai);
    }

    @Override
    public String toString() {
        return "LoaiKhuyenMai{" +
                "maLoai='" + maLoai + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
