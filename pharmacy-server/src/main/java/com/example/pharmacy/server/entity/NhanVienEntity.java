package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "NhanVien")
@Getter @Setter @NoArgsConstructor
public class NhanVienEntity {
    @Id
    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;

    @Column(name = "TenNV", nullable = false, length = 50)
    private String tenNV;

    @Column(name = "SDT", nullable = false, length = 15)
    private String sdt;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "NgaySinh", nullable = false)
    private Date ngaySinh;

    @Column(name = "GioiTinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "VaiTro", nullable = false, length = 20)
    private String vaiTro;

    @Column(name = "TrangThai", nullable = false)
    private boolean trangThai;

    @Column(name = "TaiKhoan", nullable = false, length = 50)
    private String taiKhoan;

    @Column(name = "MatKhau", nullable = false, length = 255)
    private String matKhau;

    @Column(name = "NgayVaoLam", nullable = false)
    private Date ngayVaoLam;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "TrangThaiXoa", nullable = false)
    private boolean trangThaiXoa;
}
