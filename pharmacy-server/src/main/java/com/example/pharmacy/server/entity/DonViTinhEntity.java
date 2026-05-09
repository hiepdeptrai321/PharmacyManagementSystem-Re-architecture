package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DonViTinh")
@Getter @Setter @NoArgsConstructor
public class DonViTinhEntity {
    @Id
    @Column(name = "MaDVT", nullable = false, length = 10)
    private String maDVT;

    @Column(name = "TenDonViTinh", nullable = false, length = 50)
    private String tenDonViTinh;

    @Column(name = "KiHieu", nullable = false, length = 10)
    private String kiHieu;
}
