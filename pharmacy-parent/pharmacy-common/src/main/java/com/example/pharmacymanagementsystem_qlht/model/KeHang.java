package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class KeHang implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maKe;
    private String tenKe;
    private String moTa;

    public KeHang() {
    }
    public KeHang(String maKe, String tenKe,String moTa) {
        this.maKe = maKe;
        this.tenKe = tenKe;
        this.moTa = moTa;
    }

    public String getMaKe() {
        return maKe;
    }

    public void setMaKe(String maKe) {
        this.maKe = maKe;
    }

    public String getTenKe() {
        return tenKe;
    }

    public void setTenKe(String tenKe) {
        this.tenKe = tenKe;
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
        KeHang keHang = (KeHang) o;
        return Objects.equals(maKe, keHang.maKe);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maKe);
    }

    @Override
    public String toString() {
        return "KeHang{" +
                "maKe='" + maKe + '\'' +
                ", tenKe='" + tenKe + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
