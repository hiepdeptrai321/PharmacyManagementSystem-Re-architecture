package com.example.pharmacy.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class ChiTietPhieuNhapId implements Serializable {
    private String maPN;
    private String maThuoc;
    private String maLH;
}
