package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "LuongNhanVien")
@Getter @Setter @NoArgsConstructor
public class LuongNhanVienEntity {
    @Id
    @Column(name = "MaLNV", nullable = false, length = 10)
    private String maLNV;

    @Column(name = "TuNgay", nullable = false)
    private Date tuNgay;

    @Column(name = "DenNgay")
    private Date denNgay;

    @Column(name = "LuongCoBan", nullable = false, precision = 18, scale = 2)
    private BigDecimal luongCoBan;

    @Column(name = "PhuCap", nullable = false, precision = 18, scale = 2)
    private BigDecimal phuCap;

    @Column(name = "GhiChu", nullable = false, length = 255)
    private String ghiChu;

    @Column(name = "MaNV", nullable = false, length = 10)
    private String maNV;
}
