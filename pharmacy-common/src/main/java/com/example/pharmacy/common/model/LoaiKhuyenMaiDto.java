package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class LoaiKhuyenMaiDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maLoai;
    private String tenLoai;
    private String moTa;

    public LoaiKhuyenMaiDto() {
    }

    public LoaiKhuyenMaiDto(String maLoai, String tenLoai, String moTa) {
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
        LoaiKhuyenMaiDto that = (LoaiKhuyenMaiDto) o;
        return Objects.equals(maLoai, that.maLoai);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maLoai);
    }

    @Override
    public String toString() {
        return "LoaiKhuyenMaiDto{" +
                "maLoai='" + maLoai + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
