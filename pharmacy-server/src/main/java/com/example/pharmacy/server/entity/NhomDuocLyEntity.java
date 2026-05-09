package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NhomDuocLy")
@Getter @Setter @NoArgsConstructor
public class NhomDuocLyEntity {
    @Id
    @Column(name = "MaNDL", nullable = false, length = 10)
    private String maNDL;

    @Column(name = "TenNDL", length = 50)
    private String tenNDL;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;
}
