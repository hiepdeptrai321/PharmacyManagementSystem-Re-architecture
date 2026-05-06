package com.example.pharmacy.common.enums;

import java.io.Serial;
import java.io.Serializable;

public enum BusinessCodeType implements Serializable {
    NHAN_VIEN("NHAN_VIEN"),
    KHACH_HANG("KHACH_HANG"),
    NHA_CUNG_CAP("NHA_CUNG_CAP"),
    THUOC("THUOC"),
    LO_THUOC("LO_THUOC"),
    HOA_DON("HOA_DON"),
    PHIEU_NHAP("PHIEU_NHAP"),
    PHIEU_DAT_HANG("PHIEU_DAT_HANG"),
    PHIEU_DOI_HANG("PHIEU_DOI_HANG"),
    PHIEU_TRA_HANG("PHIEU_TRA_HANG"),
    LUONG_NHAN_VIEN("LUONG_NHAN_VIEN"),
    KHUYEN_MAI("KHUYEN_MAI"),
    HOAT_DONG("HOAT_DONG");

    @Serial
    private static final long serialVersionUID = 1L;

    private final String codeType;

    BusinessCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeType() {
        return codeType;
    }
}
