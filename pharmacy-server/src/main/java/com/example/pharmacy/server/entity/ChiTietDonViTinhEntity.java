package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietDonViTinh")
@IdClass(ChiTietDonViTinhId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietDonViTinhEntity {
    @Id
    @Column(name = "MaThuoc")
    private String maThuoc;

    @Id
    @Column(name = "MaDVT")
    private String maDVT;

    @Column(name = "HeSoQuyDoi", nullable = false)
    private double heSoQuyDoi;

    @Column(name = "GiaNhap", nullable = false)
    private double giaNhap;

    @Column(name = "GiaBan", nullable = false)
    private double giaBan;

    @Column(name = "DonViCoBan", nullable = false)
    private boolean donViCoBan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDVT", insertable = false, updatable = false)
    private DonViTinhEntity donViTinh;
}
