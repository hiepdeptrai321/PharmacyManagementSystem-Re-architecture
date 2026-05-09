package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietPhieuTraHang")
@IdClass(ChiTietPhieuTraHangId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietPhieuTraHangEntity {
    @Id
    @Column(name = "MaLH", length = 10)
    private String maLH;

    @Id
    @Column(name = "MaPT", length = 10)
    private String maPT;

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

    @Column(name = "LyDoTra", nullable = false)
    private String lyDoTra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLH", insertable = false, updatable = false)
    private ThuocSPTheoLoEntity thuocSPTheoLo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPT", insertable = false, updatable = false)
    private PhieuTraHangEntity phieuTraHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDVT", insertable = false, updatable = false)
    private DonViTinhEntity donViTinh;
}
