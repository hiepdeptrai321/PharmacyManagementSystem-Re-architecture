package com.example.pharmacy.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ThongSoUngDung")
@Getter @Setter @NoArgsConstructor
public class ThongSoUngDungEntity {
    @Id
    @Column(name = "TenThongSo", nullable = false, length = 50)
    private String tenThongSo;

    @Column(name = "GiaTri", length = 255)
    private String giaTri;
}
