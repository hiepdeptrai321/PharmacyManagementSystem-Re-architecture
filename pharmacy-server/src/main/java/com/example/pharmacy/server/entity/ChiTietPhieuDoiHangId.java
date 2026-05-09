package com.example.pharmacy.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class ChiTietPhieuDoiHangId implements Serializable {
    private String maLH;
    private String maPD;
    private String maThuoc;
    private String maDVT;
}
