package com.example.pharmacymanagementsystem_qlht.model;

public class ThongKeBanHang {
    private String thoiGian;
    private int soLuongHoaDon;
    private double tongGiaTri;
    private double giamGia;
    private int soLuongDonTra;
    private double giaTriDonTra;
    private double doanhThu;

    public ThongKeBanHang() {
    }
    public ThongKeBanHang(String thoiGian, int soLuongHoaDon, double tongGiaTri, double giamGia, int soLuongDonTra, double giaTriDonTra, double doanhThu) {
        this.thoiGian = thoiGian;
        this.soLuongHoaDon = soLuongHoaDon;
        this.tongGiaTri = tongGiaTri;
        this.giamGia = giamGia;
        this.soLuongDonTra = soLuongDonTra;
        this.giaTriDonTra = giaTriDonTra;
        this.doanhThu = doanhThu;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getSoLuongHoaDon() {
        return soLuongHoaDon;
    }

    public void setSoLuongHoaDon(int soLuongHoaDon) {
        this.soLuongHoaDon = soLuongHoaDon;
    }

    public double getTongGiaTri() {
        return tongGiaTri;
    }

    public void setTongGiaTri(double tongGiaTri) {
        this.tongGiaTri = tongGiaTri;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public int getSoLuongDonTra() {
        return soLuongDonTra;
    }

    public void setSoLuongDonTra(int soLuongDonTra) {
        this.soLuongDonTra = soLuongDonTra;
    }

    public double getGiaTriDonTra() {
        return giaTriDonTra;
    }

    public void setGiaTriDonTra(double giaTriDonTra) {
        this.giaTriDonTra = giaTriDonTra;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    @Override
    public String toString() {
        return "ThongKeBanHang{" +
                "thoiGian='" + thoiGian + '\'' +
                ", soLuongHoaDon=" + soLuongHoaDon +
                ", tongGiaTri=" + tongGiaTri +
                ", giamGia=" + giamGia +
                ", soLuongDonTra=" + soLuongDonTra +
                ", giaTriDonTra=" + giaTriDonTra +
                ", doanhThu=" + doanhThu +
                '}';
    }
}
