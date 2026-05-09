package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Thuoc_SP_TangKem")
@IdClass(ThuocSPTangKemId.class)
@Getter @Setter @NoArgsConstructor
public class ThuocSPTangKemEntity {
    @Id
    @Column(name = "MaKM")
    private String maKM;

    @Id
    @Column(name = "MaThuocTangKem")
    private String maThuocTangKem;

    @Column(name = "SoLuong", nullable = false)
    private int soLuong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKM", insertable = false, updatable = false)
    private KhuyenMaiEntity khuyenMai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaThuocTangKem", referencedColumnName = "MaThuoc", insertable = false, updatable = false)
    private ThuocSanPhamEntity thuocSanPham;
}
