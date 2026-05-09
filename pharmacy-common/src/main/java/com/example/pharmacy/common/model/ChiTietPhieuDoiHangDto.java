package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ChiTietPhieuDoiHangDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Thuoc_SP_TheoLoDto loHang;
    private PhieuDoiHangDto phieuDoiHang;
    private Thuoc_SanPhamDto thuoc;
    private int soLuong;
    private DonViTinhDto dvt;
    private String lyDoDoi ;


    public ChiTietPhieuDoiHangDto() {
    }

    public ChiTietPhieuDoiHangDto(Thuoc_SP_TheoLoDto loHang, PhieuDoiHangDto phieuDoiHang, Thuoc_SanPhamDto thuoc, int soLuong, DonViTinhDto dvt, String lyDoDoi) {
        this.loHang = loHang;
        this.phieuDoiHang = phieuDoiHang;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.dvt = dvt;
        this.lyDoDoi = lyDoDoi;
    }

    public Thuoc_SP_TheoLoDto getLoHang() {
        return loHang;
    }

    public void setLoHang(Thuoc_SP_TheoLoDto loHang) {
        this.loHang = loHang;
    }

    public PhieuDoiHangDto getPhieuDoiHang() {
        return phieuDoiHang;
    }

    public void setPhieuDoiHang(PhieuDoiHangDto phieuDoiHang) {
        this.phieuDoiHang = phieuDoiHang;
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
    public DonViTinhDto getDvt() {
        return dvt;
    }

    public void setDvt(DonViTinhDto dvt) {
        this.dvt = dvt;
    }
    public String getLyDoDoi() {
        return lyDoDoi;
    }

    public void setLyDoDoi(String lyDoDoi) {
        this.lyDoDoi = lyDoDoi;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuDoiHangDto that = (ChiTietPhieuDoiHangDto) o;
        return Objects.equals(loHang, that.loHang) && Objects.equals(phieuDoiHang, that.phieuDoiHang) && Objects.equals(dvt, that.dvt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loHang, phieuDoiHang,dvt);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDoiHangDto{" +
                "loHang=" + loHang +
                ", phieuDoiHang=" + phieuDoiHang +
                ", dvt=" + dvt +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                " lyDoDoi='" + lyDoDoi +
                '}';
    }
}
