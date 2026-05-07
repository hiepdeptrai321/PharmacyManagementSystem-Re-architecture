package com.example.pharmacymanagementsystem_qlht.model;

import java.time.LocalDate;

public class ThuocHetHan {
    private String maThuocHH;
    private String tenThuocHH;
    private int soLuong;
    private LocalDate ngayHetHan;

    public ThuocHetHan() {}

    public ThuocHetHan(String maThuocHH, String tenThuocHH, int soLuong, LocalDate ngayHetHan) {
        this.maThuocHH = maThuocHH;
        this.tenThuocHH = tenThuocHH;
        this.soLuong = soLuong;
        this.ngayHetHan = ngayHetHan;
    }


    public String getMaThuocHH() { return maThuocHH; }
    public void setMaThuocHH(String maThuocHH) { this.maThuocHH = maThuocHH; }
    public String getTenThuocHH() { return tenThuocHH; }
    public void setTenThuocHH(String tenThuocHH) { this.tenThuocHH = tenThuocHH; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public LocalDate getNgayHetHan() { return ngayHetHan; }
    public void setNgayHetHan(LocalDate ngayHetHan) { this.ngayHetHan = ngayHetHan; }
}
    
