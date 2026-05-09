package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietHoaDon")
@IdClass(ChiTietHoaDonId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietHoaDonEntity {
    @Id
    @Column(name = "MaHD", length = 10)
    private String maHD;

    @Id
    @Column(name = "MaLH", length = 10)
    private String maLH;

    @Column(name = "SoLuong", nullable = false)
    private int soLuong;

    @Id
    @Column(name = "MaDVT", length = 10)
    private String maDVT;

    @Column(name = "DonGia", nullable = false)
    private double donGia;

    @Column(name = "GiamGia", nullable = false)
    private double giamGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaHD", insertable = false, updatable = false)
    private HoaDonEntity hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLH", insertable = false, updatable = false)
    private ThuocSPTheoLoEntity thuocSPTheoLo;
}
