package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NhaCungCap")
@Getter @Setter @NoArgsConstructor
public class NhaCungCapEntity {
    @Id
    @Column(name = "MaNCC", nullable = false, length = 10)
    private String maNCC;

    @Column(name = "TenNCC", nullable = false, length = 100)
    private String tenNCC;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @Column(name = "SDT", nullable = false, length = 20)
    private String sdt;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "GPKD", length = 50)
    private String gpKD;

    @Column(name = "GhiChu", columnDefinition = "TEXT")
    private String ghiChu;

    @Column(name = "TenCongTy", length = 100)
    private String tenCongTy;

    @Column(name = "MSThue", length = 20)
    private String msThue;
}
