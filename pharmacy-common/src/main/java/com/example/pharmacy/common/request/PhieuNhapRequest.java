package com.example.pharmacy.common.request;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maPhieuNhap;
    private LocalDate ngayNhap;
    private String ghiChu;
    private String maNhaCungCap;
    private final List<PhieuNhapItemRequest> items = new ArrayList<>();

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public List<PhieuNhapItemRequest> getItems() {
        return items;
    }
}
