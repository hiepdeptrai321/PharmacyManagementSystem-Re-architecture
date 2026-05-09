package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LoaiHang")
@Getter @Setter @NoArgsConstructor
public class LoaiHangEntity {
    @Id
    @Column(name = "MaLoaiHang", nullable = false, length = 10)
    private String maLoaiHang;

    @Column(name = "TenLH", length = 50)
    private String tenLH;

    @Column(name = "MoTa", length = 255)
    private String moTa;
}
