package com.example.pharmacymanagementsystem_qlht.model;

public class CTPN_TSPTL_CHTDVT {
    ChiTietDonViTinh chiTietDonViTinh;
    ChiTietPhieuNhap chiTietPhieuNhap;
    Thuoc_SP_TheoLo chiTietSP_theoLo;

    public CTPN_TSPTL_CHTDVT(ChiTietDonViTinh chiTietDonViTinh, ChiTietPhieuNhap chiTietPhieuNhap, Thuoc_SP_TheoLo chiTietSP_theoLo) {
        this.chiTietDonViTinh = chiTietDonViTinh;
        this.chiTietPhieuNhap = chiTietPhieuNhap;
        this.chiTietSP_theoLo = chiTietSP_theoLo;
    }

    public CTPN_TSPTL_CHTDVT() {
    }

    public ChiTietDonViTinh getChiTietDonViTinh() {
        return chiTietDonViTinh;
    }

    public void setChiTietDonViTinh(ChiTietDonViTinh chiTietDonViTinh) {
        this.chiTietDonViTinh = chiTietDonViTinh;
    }

    public ChiTietPhieuNhap getChiTietPhieuNhap() {
        return chiTietPhieuNhap;
    }

    public void setChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    public Thuoc_SP_TheoLo getChiTietSP_theoLo() {
        return chiTietSP_theoLo;
    }

    public void setChiTietSP_theoLo(Thuoc_SP_TheoLo chiTietSP_theoLo) {
        this.chiTietSP_theoLo = chiTietSP_theoLo;
    }

    @Override
    public String  toString() {
        return "CTPN_TSPTL_CHTDVT{" +
                "chiTietDonViTinh=" + chiTietDonViTinh +
                ", chiTietPhieuNhap=" + chiTietPhieuNhap +
                ", chiTietSP_theoLo=" + chiTietSP_theoLo +
                '}';
    }
}
