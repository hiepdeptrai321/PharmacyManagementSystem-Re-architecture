package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "HoaDon")
@Getter @Setter @NoArgsConstructor
public class HoaDonEntity {
    @Id
    @Column(name = "MaHD", nullable = false, length = 10)
    private String maHD;

    @Column(name = "NgayLap", nullable = false)
    private LocalDateTime ngayLap;

    @Column(name = "TrangThai", nullable = false, length = 10)
    private String trangThai;

    @Column(name = "LoaiHoaDon", nullable = false, length = 3)
    private String loaiHoaDon;

    @Column(name = "MaDonThuoc", length = 20)
    private String maDonThuoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH")
    private KhachHangEntity khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV")
    private NhanVienEntity nhanVien;
}
