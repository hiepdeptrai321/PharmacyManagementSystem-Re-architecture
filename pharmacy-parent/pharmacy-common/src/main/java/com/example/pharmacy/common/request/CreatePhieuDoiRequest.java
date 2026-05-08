package com.example.pharmacy.common.request;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreatePhieuDoiRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maHoaDonGoc;
    private String maKhachHang;
    private LocalDate ngayLap;
    private String ghiChu;
    private final List<CreatePhieuDoiItemRequest> items = new ArrayList<>();

    public String getMaHoaDonGoc() {
        return maHoaDonGoc;
    }

    public void setMaHoaDonGoc(String maHoaDonGoc) {
        this.maHoaDonGoc = maHoaDonGoc;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public List<CreatePhieuDoiItemRequest> getItems() {
        return items;
    }
}
