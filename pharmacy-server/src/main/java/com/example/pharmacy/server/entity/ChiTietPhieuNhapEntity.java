package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietPhieuNhap")
@IdClass(ChiTietPhieuNhapId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietPhieuNhapEntity {
    @Id
    @Column(name = "MaPN")
    private String maPN;

    @Id
    @Column(name = "MaThuoc")
    private String maThuoc;

    @Id
    @Column(name = "MaLH")
    private String maLH;

    @Column(name = "SoLuong", nullable = false)
    private int soLuong;

    @Column(name = "MaDVT")
    private String maDVT;

    @Column(name = "GiaNhap", nullable = false)
    private double giaNhap;

    @Column(name = "ChietKhau", nullable = false)
    private double chietKhau;

    @Column(name = "Thue", nullable = false)
    private double thue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPN", insertable = false, updatable = false)
    private PhieuNhapEntity phieuNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;
}
