package com.example.pharmacy.common.request;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class CreateThuocRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String tenThuoc;
    private Integer hamLuong;
    private String donViHamLuong;
    private String duongDung;
    private String quyCachDongGoi;
    private String sdkGpnk;
    private String hangSanXuat;
    private String nuocSanXuat;
    private String maLoaiHang;
    private String maNhomDuocLy;
    private String viTri;
    private boolean etc;
    private byte[] hinhAnh;
    private String maDonViCoBan = "DVT01";
    private BigDecimal heSoQuyDoiCoBan = BigDecimal.ONE;
    private BigDecimal giaNhapCoBan;
    private BigDecimal giaBanCoBan;

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public Integer getHamLuong() {
        return hamLuong;
    }

    public void setHamLuong(Integer hamLuong) {
        this.hamLuong = hamLuong;
    }

    public String getDonViHamLuong() {
        return donViHamLuong;
    }

    public void setDonViHamLuong(String donViHamLuong) {
        this.donViHamLuong = donViHamLuong;
    }

    public String getDuongDung() {
        return duongDung;
    }

    public void setDuongDung(String duongDung) {
        this.duongDung = duongDung;
    }

    public String getQuyCachDongGoi() {
        return quyCachDongGoi;
    }

    public void setQuyCachDongGoi(String quyCachDongGoi) {
        this.quyCachDongGoi = quyCachDongGoi;
    }

    public String getSdkGpnk() {
        return sdkGpnk;
    }

    public void setSdkGpnk(String sdkGpnk) {
        this.sdkGpnk = sdkGpnk;
    }

    public String getHangSanXuat() {
        return hangSanXuat;
    }

    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }

    public String getNuocSanXuat() {
        return nuocSanXuat;
    }

    public void setNuocSanXuat(String nuocSanXuat) {
        this.nuocSanXuat = nuocSanXuat;
    }

    public String getMaLoaiHang() {
        return maLoaiHang;
    }

    public void setMaLoaiHang(String maLoaiHang) {
        this.maLoaiHang = maLoaiHang;
    }

    public String getMaNhomDuocLy() {
        return maNhomDuocLy;
    }

    public void setMaNhomDuocLy(String maNhomDuocLy) {
        this.maNhomDuocLy = maNhomDuocLy;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public boolean isEtc() {
        return etc;
    }

    public void setEtc(boolean etc) {
        this.etc = etc;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaDonViCoBan() {
        return maDonViCoBan;
    }

    public void setMaDonViCoBan(String maDonViCoBan) {
        this.maDonViCoBan = maDonViCoBan;
    }

    public BigDecimal getHeSoQuyDoiCoBan() {
        return heSoQuyDoiCoBan;
    }

    public void setHeSoQuyDoiCoBan(BigDecimal heSoQuyDoiCoBan) {
        this.heSoQuyDoiCoBan = heSoQuyDoiCoBan;
    }

    public BigDecimal getGiaNhapCoBan() {
        return giaNhapCoBan;
    }

    public void setGiaNhapCoBan(BigDecimal giaNhapCoBan) {
        this.giaNhapCoBan = giaNhapCoBan;
    }

    public BigDecimal getGiaBanCoBan() {
        return giaBanCoBan;
    }

    public void setGiaBanCoBan(BigDecimal giaBanCoBan) {
        this.giaBanCoBan = giaBanCoBan;
    }
}
