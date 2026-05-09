package com.example.pharmacy.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class ChiTietHoaDonId implements Serializable {
    private String maHD;
    private String maLH;
    private String maDVT;
}
