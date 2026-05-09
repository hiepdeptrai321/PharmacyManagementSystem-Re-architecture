package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "KhachHang")
@Getter @Setter @NoArgsConstructor
public class KhachHangEntity {
    @Id
    @Column(name = "MaKH", nullable = false, length = 10)
    private String maKH;

    @Column(name = "TenKH", nullable = false, length = 50)
    private String tenKH;

    @Column(name = "SDT", nullable = false, length = 15)
    private String sdt;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh", nullable = false)
    private boolean gioiTinh;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "TrangThai", nullable = false)
    private boolean trangThai;
}
