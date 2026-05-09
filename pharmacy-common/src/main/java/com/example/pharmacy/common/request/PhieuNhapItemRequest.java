package com.example.pharmacy.common.request;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PhieuNhapItemRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maThuoc;
    private String maLo;
    private String maDvt;
    private int soLuong;
    private BigDecimal giaNhap;
    private BigDecimal chietKhau = BigDecimal.ZERO;
    private BigDecimal thue = BigDecimal.ZERO;
    private BigDecimal giaBanCapNhat;
    private LocalDate nsx;
    private LocalDate hsd;

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getMaLo() {
        return maLo;
    }

    public void setMaLo(String maLo) {
        this.maLo = maLo;
    }

    public String getMaDvt() {
        return maDvt;
    }

    public void setMaDvt(String maDvt) {
        this.maDvt = maDvt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(BigDecimal chietKhau) {
        this.chietKhau = chietKhau;
    }

    public BigDecimal getThue() {
        return thue;
    }

    public void setThue(BigDecimal thue) {
        this.thue = thue;
    }

    public BigDecimal getGiaBanCapNhat() {
        return giaBanCapNhat;
    }

    public void setGiaBanCapNhat(BigDecimal giaBanCapNhat) {
        this.giaBanCapNhat = giaBanCapNhat;
    }

    public LocalDate getNsx() {
        return nsx;
    }

    public void setNsx(LocalDate nsx) {
        this.nsx = nsx;
    }

    public LocalDate getHsd() {
        return hsd;
    }

    public void setHsd(LocalDate hsd) {
        this.hsd = hsd;
    }
}
