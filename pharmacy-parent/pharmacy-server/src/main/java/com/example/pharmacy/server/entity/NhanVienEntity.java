package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NhanVien")
public class NhanVienEntity {
    @Id
    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;

    @Column(name = "TaiKhoan", nullable = false, length = 50)
    private String taiKhoan;

    @Column(name = "MatKhau", nullable = false, length = 50)
    private String matKhau;

    @Column(name = "TenNV", nullable = false, length = 50)
    private String tenNV;

    @Column(name = "VaiTro", nullable = false, length = 20)
    private String vaiTro;

    @Column(name = "TrangThai", nullable = false)
    private boolean trangThai;

    @Column(name = "TrangThaiXoa", nullable = false)
    private boolean trangThaiXoa;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }
}
