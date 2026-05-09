package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "KeHang")
@Getter @Setter @NoArgsConstructor
public class KeHangEntity {
    @Id
    @Column(name = "MaKe", nullable = false, length = 10)
    private String maKe;

    @Column(name = "TenKe", length = 50)
    private String tenKe;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;
}
