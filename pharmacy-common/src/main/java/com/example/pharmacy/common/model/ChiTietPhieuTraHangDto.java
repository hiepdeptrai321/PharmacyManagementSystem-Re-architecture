package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietPhieuTraHangDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SP_TheoLoDto loHang;
    private PhieuTraHangDto phieuTraHang;
    private  Thuoc_SanPhamDto thuoc;
    private DonViTinhDto dvt;
    private int soLuong;
    private double donGia;
    private double giamGia;
    private String lyDo;
    public ChiTietPhieuTraHangDto() {

    }

    public ChiTietPhieuTraHangDto(Thuoc_SP_TheoLoDto loHang, PhieuTraHangDto phieuTraHang, Thuoc_SanPhamDto thuoc, DonViTinhDto dvt, int soLuong, double donGia, double giamGia, String lyDo) {
        this.loHang = loHang;
        this.phieuTraHang = phieuTraHang;
        this.thuoc = thuoc;
        this.dvt = dvt;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.giamGia = giamGia;
        this.lyDo = lyDo;
    }



    public Thuoc_SP_TheoLoDto getLoHang() {
        return loHang;
    }

    public void setLoHang(Thuoc_SP_TheoLoDto loHang) {
        this.loHang = loHang;
    }

    public PhieuTraHangDto getPhieuTraHang() {
        return phieuTraHang;
    }

    public void setPhieuTraHang(PhieuTraHangDto phieuTraHang) {
        this.phieuTraHang = phieuTraHang;
    }

    public Thuoc_SanPhamDto getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc_SanPhamDto thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }
    public DonViTinhDto getDvt() {
        return dvt;
    }
    public void setDvt(DonViTinhDto dvt) {
        this.dvt = dvt;
    }
    public String getLyDoTra() {
        return lyDo;
    }
    public void setLyDoTra(String lyDo) {
        this.lyDo = lyDo;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuTraHangDto that = (ChiTietPhieuTraHangDto) o;
        return Objects.equals(loHang, that.loHang) && Objects.equals(phieuTraHang, that.phieuTraHang) && Objects.equals(dvt, that.dvt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loHang, phieuTraHang, dvt);
    }

    public double getThanhTienTra() {
        return soLuong* donGia;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuTraHangDto{" +
                "loHang=" + loHang +
                ", phieuTraHang=" + phieuTraHang +
                ", thuoc=" + thuoc +
                ", dvt=" + dvt +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", giamGia=" + giamGia +
                ", lyDo='" + lyDo + '\'' +
                '}';
    }
}
