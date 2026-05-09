package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class HoaDonDisplay implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String maHD;
    private LocalDate ngayLap;
    private String maKH;
    private String maNV;
    private double tongTien;

    public HoaDonDisplay(String maHD, LocalDate ngayLap, String maKH, String maNV, double tongTien) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    // Getters
    public String getMaHD() { return maHD; }
    public LocalDate getNgayLap() { return ngayLap; }
    public String getMaKH() { return maKH; }
    public String getMaNV() { return maNV; }
    public double getTongTien() { return tongTien; }
}
