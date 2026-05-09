package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Thuoc_SP_TheoLo")
@Getter @Setter @NoArgsConstructor
public class ThuocSPTheoLoEntity {
    @Column(name = "MaPN", length = 10)
    private String maPN;

    @Column(name = "MaThuoc", length = 10)
    private String maThuoc;

    @Id
    @Column(name = "MaLH", nullable = false, length = 10)
    private String maLH;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

    @Column(name = "SoLuongDat")
    private Integer soLuongDat;

    @Column(name = "SoLuongGiu")
    private Integer soLuongGiu;

    @Column(name = "NSX")
    private LocalDate nsx;

    @Column(name = "HSD")
    private LocalDate hsd;
}
