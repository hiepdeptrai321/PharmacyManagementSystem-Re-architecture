package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "HoatDong")
@Getter @Setter @NoArgsConstructor
public class HoatDongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "MaHDong", unique = true, length = 16)
    private String maHDong;

    @Column(name = "LoaiHD", length = 20)
    private String loaiHD;

    @Column(name = "ThoiGian")
    private LocalDateTime thoiGian;

    @Column(name = "BangDL", length = 50)
    private String bangDL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV")
    private NhanVienEntity nhanVien;

    @Column(name = "NoiDung", columnDefinition = "TEXT")
    private String noiDung;
}
