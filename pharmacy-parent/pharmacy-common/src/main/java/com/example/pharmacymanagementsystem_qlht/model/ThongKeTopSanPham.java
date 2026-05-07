package com.example.pharmacymanagementsystem_qlht.model;

public class ThongKeTopSanPham {
    private int stt;            // Số thứ tự xếp hạng
    private String maThuoc;
    private String tenThuoc;
    private String donViTinh;   // Đơn vị tính cơ bản hoặc đơn vị bán
    private int soLuong;        // Số lượng bán ra
    private double tongTien;    // Doanh thu (Sau khi trừ giảm giá)

    public ThongKeTopSanPham() { }

    public ThongKeTopSanPham(int stt, String maThuoc, String tenThuoc, String donViTinh, int soLuong, double tongTien) {
        this.stt = stt;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    // Getters and Setters
    public int getStt() { return stt; }
    public void setStt(int stt) { this.stt = stt; }

    public String getMaThuoc() { return maThuoc; }
    public void setMaThuoc(String maThuoc) { this.maThuoc = maThuoc; }

    public String getTenThuoc() { return tenThuoc; }
    public void setTenThuoc(String tenThuoc) { this.tenThuoc = tenThuoc; }

    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}