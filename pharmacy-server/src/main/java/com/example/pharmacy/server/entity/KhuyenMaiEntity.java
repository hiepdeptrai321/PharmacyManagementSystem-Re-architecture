package com.example.pharmacy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "KhuyenMai")
@Getter @Setter @NoArgsConstructor
public class KhuyenMaiEntity {
    @Id
    @Column(name = "MaKM", nullable = false, length = 10)
    private String maKM;

    @Column(name = "TenKM", nullable = false, length = 50)
    private String tenKM;

    @Column(name = "GiaTriKM")
    private double giaTriKM;

    @Column(name = "GiaTriApDung")
    private double giaTriApDung;

    @Column(name = "LoaiGiaTri", length = 10)
    private String loaiGiaTri;

    @Column(name = "NgayBatDau", nullable = false)
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc", nullable = false)
    private LocalDate ngayKetThuc;

    @Column(name = "MoTa", length = 255)
    private String moTa;

    @Column(name = "NgayTao", nullable = false)
    private LocalDateTime ngayTao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLoai")
    private LoaiKhuyenMaiEntity loaiKhuyenMai;
}
