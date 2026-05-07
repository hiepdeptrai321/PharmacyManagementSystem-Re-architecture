package com.example.pharmacymanagementsystem_qlht.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class NhanVien implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maNV;
    private String tenNV;
    private String sdt;
    private String email;
    private Date ngaySinh;
    private boolean gioiTinh;
    private String diaChi;
    private boolean trangThai;
    private String taiKhoan;
    private String matKhau;
    private Date ngayVaoLam;
    private Date ngayNghiLam;
    private boolean trangThaiXoa;
    private String vaiTro;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String sdt, String email, Date ngaySinh, boolean gioiTinh, String diaChi, boolean trangThai, String taiKhoan, String matKhau, Date ngayVaoLam, Date ngayNghiLam,boolean trangThaiXoa, String vaiTro) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ngayVaoLam = ngayVaoLam;
        this.ngayNghiLam = ngayNghiLam;
        this.trangThaiXoa = trangThaiXoa;
        this.vaiTro = vaiTro;
    }

    public NhanVien(NhanVien nhanVien) {
        this.maNV = nhanVien.maNV;
        this.tenNV = nhanVien.tenNV;
        this.sdt = nhanVien.sdt;
        this.email = nhanVien.email;
        this.ngaySinh = nhanVien.ngaySinh;
        this.gioiTinh = nhanVien.gioiTinh;
        this.diaChi = nhanVien.diaChi;
        this.trangThai = nhanVien.trangThai;
        this.taiKhoan = nhanVien.taiKhoan;
        this.matKhau = nhanVien.matKhau;
        this.ngayVaoLam = nhanVien.ngayVaoLam;
        this.ngayNghiLam = nhanVien.ngayNghiLam;
        this.trangThaiXoa = nhanVien.trangThaiXoa;
        this.vaiTro = nhanVien.vaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
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

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public Date getNgayNghiLam() {
        return ngayNghiLam;
    }

    public void setNgayNghiLam(Date ngayNghiLam) {
        this.ngayNghiLam = ngayNghiLam;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public String getVaiTro() {
        return vaiTro;
    }
    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return Objects.equals(maNV, nhanVien.maNV);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maNV);
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "maNV='" + maNV + '\'' +
                ", tenNV='" + tenNV + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", ngaySinh=" + ngaySinh +
                ", gioiTinh=" + gioiTinh +
                ", diaChi='" + diaChi + '\'' +
                ", trangThai=" + trangThai +
                ", taiKhoan='" + taiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", ngayVaoLam=" + ngayVaoLam +
                ", ngayNghiLam=" + ngayNghiLam +
                '}';
    }
}
