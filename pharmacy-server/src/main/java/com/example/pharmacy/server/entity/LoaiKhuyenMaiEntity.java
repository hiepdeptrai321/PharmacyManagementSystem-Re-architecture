package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LoaiKhuyenMai")
@Getter @Setter @NoArgsConstructor
public class LoaiKhuyenMaiEntity {
    @Id
    @Column(name = "MaLoai", nullable = false, length = 10)
    private String maLoai;

    @Column(name = "TenLoai", length = 50)
    private String tenLoai;

    @Column(name = "MoTa", length = 255)
    private String moTa;
}
