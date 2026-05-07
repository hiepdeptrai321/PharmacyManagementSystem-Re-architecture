package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class LoaiHang implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maLoaiHang;
    private String tenLoaiHang;
    private String moTa;

    public LoaiHang() {
    }

    public LoaiHang(String maLoaiHang, String tenLoaiHang, String moTa) {
        this.maLoaiHang = maLoaiHang;
        this.tenLoaiHang = tenLoaiHang;
        this.moTa = moTa;
    }

    public String getMaLoaiHang() {
        return maLoaiHang;
    }

    public void setMaLoaiHang(String maLoaiHang) {
        this.maLoaiHang = maLoaiHang;
    }

    public String getTenLoaiHang() {
        return tenLoaiHang;
    }

    public void setTenLoaiHang(String tenLoaiHang) {
        this.tenLoaiHang = tenLoaiHang;
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
        LoaiHang loaiHang = (LoaiHang) o;
        return Objects.equals(maLoaiHang, loaiHang.maLoaiHang);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maLoaiHang);
    }

    @Override
    public String toString() {
        return "LoaiHang{" +
                "maLoaiHang='" + maLoaiHang + '\'' +
                ", tenLoaiHang='" + tenLoaiHang + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
