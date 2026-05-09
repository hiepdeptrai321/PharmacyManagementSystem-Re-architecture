package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietPhieuDoiHang")
@IdClass(ChiTietPhieuDoiHangId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietPhieuDoiHangEntity {
    @Id
    @Column(name = "MaLH", length = 10)
    private String maLH;

    @Id
    @Column(name = "MaPD", length = 10)
    private String maPD;

    @Id
    @Column(name = "MaThuoc", length = 10)
    private String maThuoc;

    @Column(name = "SoLuong", nullable = false)
    private int soLuong;

    @Id
    @Column(name = "MaDVT", length = 10)
    private String maDVT;

    @Column(name = "LyDoDoi", nullable = false)
    private String lyDoDoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLH", insertable = false, updatable = false)
    private ThuocSPTheoLoEntity thuocSPTheoLo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPD", insertable = false, updatable = false)
    private PhieuDoiHangEntity phieuDoiHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDVT", insertable = false, updatable = false)
    private DonViTinhEntity donViTinh;
}
