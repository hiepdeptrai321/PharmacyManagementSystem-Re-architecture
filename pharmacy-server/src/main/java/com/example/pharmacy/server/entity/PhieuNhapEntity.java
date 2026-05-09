package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "PhieuNhap")
@Getter @Setter @NoArgsConstructor
public class PhieuNhapEntity {
    @Id
    @Column(name = "MaPN", nullable = false, length = 10)
    private String maPN;

    @Column(name = "NgayNhap", nullable = false)
    private LocalDate ngayNhap;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @Column(name = "GhiChu", length = 255)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNCC")
    private NhaCungCapEntity nhaCungCap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV")
    private NhanVienEntity nhanVien;
}
