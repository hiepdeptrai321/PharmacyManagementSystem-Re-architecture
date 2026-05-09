package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Thuoc_SP_TheoLo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maLH;
    private int soLuongTon;
    private Date nsx;
    private Date hsd;
    private ChiTietPhieuNhap phieuNhap;
    private Thuoc_SanPham thuoc;

    public Thuoc_SP_TheoLo(){

    }

    public Thuoc_SP_TheoLo(String maLH, int soLuongTon, Date nsx, Date hsd, ChiTietPhieuNhap phieuNhap, Thuoc_SanPham thuoc) {
        this.maLH = maLH;
        this.soLuongTon = soLuongTon;
        this.nsx = nsx;
        this.hsd = hsd;
        this.phieuNhap = phieuNhap;
        this.thuoc = thuoc;
    }

    public String getMaLH() {
        return maLH;
    }

    public void setMaLH(String maLH) {
        this.maLH = maLH;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public Date getNsx() {
        return nsx;
    }

    public void setNsx(Date nsx) {
        this.nsx = nsx;
    }

    public Date getHsd() {
        return hsd;
    }

    public void setHsd(Date hsd) {
        this.hsd = hsd;
    }

    public ChiTietPhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(ChiTietPhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }


    public Thuoc_SanPham getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPham thuoc) {
        this.thuoc = thuoc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Thuoc_SP_TheoLo that = (Thuoc_SP_TheoLo) o;
        return Objects.equals(maLH, that.maLH);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maLH);
    }

    @Override
    public String toString() {
        return "Thuoc_SP_TheoLo{" +
                "maLH='" + maLH + '\'' +
                ", soLuongTon=" + soLuongTon +
                ", nsx=" + nsx +
                ", hsd=" + hsd +
                ", phieuNhap=" + phieuNhap +
                ", thuoc=" + thuoc +
                '}';
    }
}
