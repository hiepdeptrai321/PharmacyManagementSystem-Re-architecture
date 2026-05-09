package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class HoaDonDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maHD;
    private NhanVienDto nhanVien;
    private Timestamp ngayLap;
    private List<ChiTietHoaDonDto> chiTietHD;
    private KhachHangDto khachHang;
    private Boolean trangThai;
    private String loaiHoaDon; // MỚI
    private String maDonThuoc;

    public HoaDonDto() {
    }

    public HoaDonDto(String maHD, NhanVienDto maNV, Timestamp ngayLap, KhachHangDto maKH, Boolean trangThai) {
        this.maHD = maHD;
        this.nhanVien = maNV;
        this.ngayLap = ngayLap;
        this.khachHang = maKH;
        this.trangThai = trangThai;
    }
    public double tongHD() {
        if (chiTietHD == null || chiTietHD.isEmpty()) {
            return 0.0;
        }
        return chiTietHD.stream()
                .mapToDouble(ChiTietHoaDonDto::tinhThanhTien)
                .sum();
    }

    // Thêm Setter/Getter cho ChiTietHoaDonDto
    public List<ChiTietHoaDonDto> getChiTietHD() {
        return chiTietHD;
    }

    public void setChiTietHD(List<ChiTietHoaDonDto> chiTietHD) {
        this.chiTietHD = chiTietHD;
    }
    public String getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(String loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public String getMaDonThuoc() {
        return maDonThuoc;
    }

    public void setMaDonThuoc(String maDonThuoc) {
        this.maDonThuoc = maDonThuoc;
    }
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public NhanVienDto getMaNV() {
        return nhanVien;
    }

    public void setMaNV(NhanVienDto maNV) {
        this.nhanVien = maNV;
    }

    public Timestamp getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Timestamp ngayLap) {
        this.ngayLap = ngayLap;
    }

    public KhachHangDto getMaKH() {
        return khachHang;
    }

    public void setMaKH(KhachHangDto maKH) {
        this.khachHang = maKH;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
    public double tinhTongTien(List<ChiTietHoaDonDto> dsCT) {
        return dsCT.stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia())
                .sum();
    }
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HoaDonDto hoaDon = (HoaDonDto) o;
        return Objects.equals(maHD, hoaDon.maHD);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maHD);
    }

    @Override
    public String toString() {
        return "HoaDonDto{" +
                "maHD='" + maHD + '\'' +
                ", nhanVien=" + nhanVien +
                ", ngayLap=" + ngayLap +
                ", khachHang=" + khachHang +
                ", trangThai=" + trangThai +
                '}';
    }
}
