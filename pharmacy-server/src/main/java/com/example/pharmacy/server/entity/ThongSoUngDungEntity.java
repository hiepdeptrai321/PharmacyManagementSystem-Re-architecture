package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ThongSoUngDung")
public class ThongSoUngDungEntity {
    @Id
    @Column(name = "TenThongSo", nullable = false, length = 50)
    private String tenThongSo;

    @Column(name = "GiaTri", length = 255)
    private String giaTri;

    public String getTenThongSo() {
        return tenThongSo;
    }

    public void setTenThongSo(String tenThongSo) {
        this.tenThongSo = tenThongSo;
    }

    public String getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(String giaTri) {
        this.giaTri = giaTri;
    }
}
