package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ThuocTonKhoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maThuoc;
    private String tenThuoc;
    private String donViTinh;
    private int soLoTon;
    private int tongSoLuongTon;

    public ThuocTonKhoDto() {}

    public ThuocTonKhoDto(String maThuoc, String tenThuoc, String donViTinh, int soLoTon, int tongSoLuongTon) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLoTon = soLoTon;
        this.tongSoLuongTon = tongSoLuongTon;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public int getSoLoTon() {
        return soLoTon;
    }

    public void setSoLoTon(int soLoTon) {
        this.soLoTon = soLoTon;
    }

    public int getTongSoLuongTon() {
        return tongSoLuongTon;
    }

    public void setTongSoLuongTon(int tongSoLuongTon) {
        this.tongSoLuongTon = tongSoLuongTon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ThuocTonKhoDto that = (ThuocTonKhoDto) o;
        return Objects.equals(maThuoc, that.maThuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maThuoc);
    }

    @Override
    public String toString() {
        return "ThuocTonKhoDto{" +
                "maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", soLoTon=" + soLoTon +
                ", tongSoLuongTon=" + tongSoLuongTon +
                '}';
    }
}
