package com.example.pharmacymanagementsystem_qlht.model;

public class ThongKeSanPham {
    private String maThuoc;
    private String tenThuoc;
    private int soLuong;
    private double thanhTien;

    public ThongKeSanPham() {
    }

    public ThongKeSanPham(String maThuoc, String tenThuoc, int soLuong, double thanhTien) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    // --- Getters and Setters ---
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}