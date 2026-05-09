package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ChiTietKhuyenMai")
@IdClass(ChiTietKhuyenMaiId.class)
@Getter @Setter @NoArgsConstructor
public class ChiTietKhuyenMaiEntity {
    @Id
    @Column(name = "MaThuoc")
    private String maThuoc;

    @Id
    @Column(name = "MaKM")
    private String maKM;

    @Column(name = "SLApDung", nullable = false)
    private int slApDung;

    @Column(name = "SLToiDa", nullable = false)
    private int slToiDa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKM", insertable = false, updatable = false)
    private KhuyenMaiEntity khuyenMai;
}
