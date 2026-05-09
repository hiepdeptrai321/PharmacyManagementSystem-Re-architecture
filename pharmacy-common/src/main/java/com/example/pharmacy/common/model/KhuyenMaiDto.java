package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class KhuyenMaiDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maKM;
    private LoaiKhuyenMaiDto loaiKM;
    private String tenKM;
    private float giaTriKM;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String moTa;
    private Timestamp ngayTao;
    private double giaTriApDung;

    public KhuyenMaiDto() {
    }

    public KhuyenMaiDto(String maKM, LoaiKhuyenMaiDto loaiKM, String tenKM, float giaTriKM, Date ngayBatDau, Date ngayKetThuc, String moTa) {
        this.maKM = maKM;
        this.loaiKM = loaiKM;
        this.tenKM = tenKM;
        this.giaTriKM = giaTriKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
    }

    public KhuyenMaiDto(String maKM, LoaiKhuyenMaiDto loaiKM, String tenKM, float giaTriKM, Date ngayBatDau, Date ngayKetThuc, String moTa, Timestamp ngayTao, double giaTriApDung) {
        this.maKM = maKM;
        this.loaiKM = loaiKM;
        this.tenKM = tenKM;
        this.giaTriKM = giaTriKM;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.moTa = moTa;
        this.ngayTao = ngayTao;
        this.giaTriApDung = giaTriApDung;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {this.maKM = maKM; }

    public LoaiKhuyenMaiDto getLoaiKM() {
        return loaiKM;
    }

    public void setLoaiKM(LoaiKhuyenMaiDto loaiKM) {
        this.loaiKM = loaiKM;
    }

    public String getTenKM() {
        return tenKM;
    }

    public void setTenKM(String tenKM) {
        this.tenKM = tenKM;
    }

    public float getGiaTriKM() {
        return giaTriKM;
    }

    public void setGiaTriKM(float giaTriKM) {
        this.giaTriKM = giaTriKM;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }
    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getGiaTriApDung() {
        return giaTriApDung;
    }

    public void setGiaTriApDung(double giaTriApDung) {
        this.giaTriApDung = giaTriApDung;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KhuyenMaiDto khuyenMai = (KhuyenMaiDto) o;
        return Objects.equals(maKM, khuyenMai.maKM);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maKM);
    }

    @Override
    public String toString() {
        return "KhuyenMaiDto{" +
                "maKM='" + maKM + '\'' +
                ", loaiKM=" + loaiKM +
                ", tenKM='" + tenKM + '\'' +
                ", giaTriKM=" + giaTriKM +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", moTa='" + moTa + '\'' +
                '}';
    }
}
