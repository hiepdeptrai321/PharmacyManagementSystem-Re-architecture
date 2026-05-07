package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "KeHang")
public class KeHangEntity {
    @Id
    @Column(name = "MaKe", nullable = false, length = 10)
    private String maKe;

    @Column(name = "TenKe", length = 50)
    private String tenKe;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

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
}
