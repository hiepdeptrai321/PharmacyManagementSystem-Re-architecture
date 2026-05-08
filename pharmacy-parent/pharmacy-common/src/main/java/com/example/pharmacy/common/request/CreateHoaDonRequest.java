package com.example.pharmacy.common.request;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateHoaDonRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maHoaDon;
    private String maKhachHang;
    private LocalDateTime ngayLap;
    private String loaiHoaDon;
    private String maDonThuoc;
    private String phuongThucThanhToan;
    private BigDecimal tienKhachDua = BigDecimal.ZERO;
    private BigDecimal vatRate = BigDecimal.ZERO;
    private BigDecimal discountInvoiceAmount = BigDecimal.ZERO;
    private String maPhieuDat;
    private final List<CreateHoaDonLineRequest> lines = new ArrayList<>();

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public LocalDateTime getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDateTime ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public String getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(String maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public BigDecimal getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(BigDecimal tienKhachDua) {
        this.tienKhachDua = tienKhachDua == null ? BigDecimal.ZERO : tienKhachDua;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate == null ? BigDecimal.ZERO : vatRate;
    }

    public BigDecimal getDiscountInvoiceAmount() {
        return discountInvoiceAmount;
    }

    public void setDiscountInvoiceAmount(BigDecimal discountInvoiceAmount) {
        this.discountInvoiceAmount = discountInvoiceAmount == null ? BigDecimal.ZERO : discountInvoiceAmount;
    }

    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public List<CreateHoaDonLineRequest> getLines() {
        return lines;
    }
}
