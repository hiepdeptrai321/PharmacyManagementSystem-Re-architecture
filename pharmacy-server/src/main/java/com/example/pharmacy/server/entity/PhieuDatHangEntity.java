package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "PhieuDatHang")
@Getter @Setter @NoArgsConstructor
public class PhieuDatHangEntity {
    @Id
    @Column(name = "MaPDat", nullable = false, length = 10)
    private String maPDat;

    @Column(name = "NgayLap", nullable = false)
    private LocalDate ngayLap;

    @Column(name = "SoTienCoc")
    private double soTienCoc;

    @Column(name = "GhiChu", length = 255)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH")
    private KhachHangEntity khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV")
    private NhanVienEntity nhanVien;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
