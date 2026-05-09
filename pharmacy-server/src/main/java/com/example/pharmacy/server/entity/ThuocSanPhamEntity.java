package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Thuoc_SanPham")
@Getter @Setter @NoArgsConstructor
public class ThuocSanPhamEntity {
    @Id
    @Column(name = "MaThuoc", nullable = false, length = 10)
    private String maThuoc;

    @Column(name = "TenThuoc", length = 100)
    private String tenThuoc;

    @Column(name = "HamLuong")
    private Integer hamLuong;

    @Column(name = "DonViHL", length = 20)
    private String donViHL;

    @Column(name = "DuongDung", length = 20)
    private String duongDung;

    @Column(name = "QuyCachDongGoi", length = 100)
    private String quyCachDongGoi;

    @Column(name = "SDK_GPNK", length = 20)
    private String sdkGpnk;

    @Column(name = "HangSX", length = 30)
    private String hangSX;

    @Column(name = "NuocSX", length = 20)
    private String nuocSX;

    @Lob
    @Column(name = "HinhAnh")
    private byte[] hinhAnh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLoaiHang")
    private LoaiHangEntity loaiHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNDL")
    private NhomDuocLyEntity nhomDuocLy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ViTri")
    private KeHangEntity keHang;

    @Column(name = "TrangThaiXoa", nullable = false)
    private boolean trangThaiXoa;

    @Column(name = "ETC", nullable = false)
    private boolean etc;
}
