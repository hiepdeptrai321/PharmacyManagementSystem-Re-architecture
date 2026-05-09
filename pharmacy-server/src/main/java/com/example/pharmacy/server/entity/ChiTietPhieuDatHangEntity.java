package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietPhieuDatHang")
@IdClass(ChiTietPhieuDatHangId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietPhieuDatHangEntity {
    @Id
    @Column(name = "MaPDat", length = 10)
    private String maPDat;

    @Id
    @Column(name = "MaThuoc", length = 10)
    private String maThuoc;

    @Column(name = "SoLuong", nullable = false)
    private int soLuong;

    @Id
    @Column(name = "MaDVT", length = 10)
    private String maDVT;

    @Column(name = "DonGia", nullable = false)
    private double donGia;

    @Column(name = "GiamGia", nullable = false)
    private double giamGia;

    @Column(name = "TrangThai")
    private boolean trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPDat", insertable = false, updatable = false)
    private PhieuDatHangEntity phieuDatHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;
}
