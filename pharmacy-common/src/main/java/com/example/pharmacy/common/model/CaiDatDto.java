package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;

public class CaiDatDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String tenThongSo;
    private String giaTri;

    public CaiDatDto() {
    }

    public CaiDatDto(String tenThongSo, String giaTri) {
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
