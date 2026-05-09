package com.example.pharmacy.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class ThuocSPTangKemId implements Serializable {
    private String maKM;
    private String maThuocTangKem;
}
