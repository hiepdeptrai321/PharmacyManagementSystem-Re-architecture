package com.example.pharmacy.common.model;

public class CTPN_TSPTL_CHTDVTDto {
    ChiTietDonViTinhDto chiTietDonViTinh;
    ChiTietPhieuNhapDto chiTietPhieuNhap;
    Thuoc_SP_TheoLoDto chiTietSP_theoLo;

    public CTPN_TSPTL_CHTDVTDto(ChiTietDonViTinhDto chiTietDonViTinh, ChiTietPhieuNhapDto chiTietPhieuNhap, Thuoc_SP_TheoLoDto chiTietSP_theoLo) {
        this.chiTietDonViTinh = chiTietDonViTinh;
        this.chiTietPhieuNhap = chiTietPhieuNhap;
        this.chiTietSP_theoLo = chiTietSP_theoLo;
    }

    public CTPN_TSPTL_CHTDVTDto() {
    }

    public ChiTietDonViTinhDto getChiTietDonViTinh() {
        return chiTietDonViTinh;
    }

    public void setChiTietDonViTinh(ChiTietDonViTinhDto chiTietDonViTinh) {
        this.chiTietDonViTinh = chiTietDonViTinh;
    }

    public ChiTietPhieuNhapDto getChiTietPhieuNhap() {
        return chiTietPhieuNhap;
    }

    public void setChiTietPhieuNhap(ChiTietPhieuNhapDto chiTietPhieuNhap) {
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    public Thuoc_SP_TheoLoDto getChiTietSP_theoLo() {
        return chiTietSP_theoLo;
    }

    public void setChiTietSP_theoLo(Thuoc_SP_TheoLoDto chiTietSP_theoLo) {
        this.chiTietSP_theoLo = chiTietSP_theoLo;
    }

    @Override
    public String  toString() {
        return "CTPN_TSPTL_CHTDVTDto{" +
                "chiTietDonViTinh=" + chiTietDonViTinh +
                ", chiTietPhieuNhap=" + chiTietPhieuNhap +
                ", chiTietSP_theoLo=" + chiTietSP_theoLo +
                '}';
    }
}
