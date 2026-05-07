package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;

public class CaiDat implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String tenThongSo;
    private String giaTri;

    public CaiDat() {
    }

    public CaiDat(String tenThongSo, String giaTri) {
        this.tenThongSo = tenThongSo;
        this.giaTri = giaTri;
    }

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
