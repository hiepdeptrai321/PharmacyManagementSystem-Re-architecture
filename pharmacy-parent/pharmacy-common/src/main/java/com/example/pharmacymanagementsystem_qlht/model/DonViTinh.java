package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class DonViTinh implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maDVT;
    private String tenDonViTinh;
    private String kiHieu;

    public DonViTinh() {
    }

    public DonViTinh(String maDVT, String tenDonViTinh, String kiHieu) {
        this.maDVT = maDVT;
        this.tenDonViTinh = tenDonViTinh;
        this.kiHieu = kiHieu;
    }

    public String getMaDVT() {
        return maDVT;
    }

    public void setMaDVT(String maDVT) {
        this.maDVT = maDVT;
    }

    public String getTenDonViTinh() {
        return tenDonViTinh;
    }

    public void setTenDonViTinh(String tenDonViTinh) {
        this.tenDonViTinh = tenDonViTinh;
    }

    public String getKiHieu() {
        return kiHieu;
    }

    public void setKiHieu(String kiHieu) {
        this.kiHieu = kiHieu;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DonViTinh donViTinh = (DonViTinh) o;
        return Objects.equals(maDVT, donViTinh.maDVT);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maDVT);
    }

    @Override
    public String toString() {
        return "DonViTinh{" +
                "maDVT='" + maDVT + '\'' +
                ", tenDonViTinh='" + tenDonViTinh + '\'' +
                ", kiHieu='" + kiHieu + '\'' +
                '}';
    }
}
