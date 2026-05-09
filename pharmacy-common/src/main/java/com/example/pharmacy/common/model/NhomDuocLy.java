package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class NhomDuocLy implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maNDL;
    private String tenNDL;
    private String moTa;

    public NhomDuocLy(){

    }
    public NhomDuocLy(String maNDL, String tenNDL, String moTa) {
        this.maNDL = maNDL;
        this.tenNDL = tenNDL;
        this.moTa = moTa;
    }

    public String getMaNDL() {
        return maNDL;
    }

    public void setMaNDL(String maNDL) {
        this.maNDL = maNDL;
    }

    public String getTenNDL() {
        return tenNDL;
    }

    public void setTenNDL(String tenNDL) {
        this.tenNDL = tenNDL;
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
        NhomDuocLy that = (NhomDuocLy) o;
        return Objects.equals(maNDL, that.maNDL) && Objects.equals(tenNDL, that.tenNDL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNDL, tenNDL);
    }

    @Override
    public String toString() {
        return "NhomDuocLy{" +
                "maNDL='" + maNDL + '\'' +
                ", tenNDL='" + tenNDL + '\'' +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
