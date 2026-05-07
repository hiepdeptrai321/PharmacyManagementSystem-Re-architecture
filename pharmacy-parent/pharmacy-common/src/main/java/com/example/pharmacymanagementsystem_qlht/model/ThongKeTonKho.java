package com.example.pharmacymanagementsystem_qlht.model;

public class ThongKeTonKho {
    private String maThuoc;
    private String tenThuoc;
    private String dvt; // Don Vi Tinh (Kí hiệu)
    private int tonDauKy;
    private int nhapTrongKy;
    private int xuatTrongKy;
    private int tonCuoiKy;

    public ThongKeTonKho() {}

    public ThongKeTonKho(String maThuoc, String tenThuoc, String dvt, int tonDauKy, int nhapTrongKy, int xuatTrongKy, int tonCuoiKy) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.dvt = dvt;
        this.tonDauKy = tonDauKy;
        this.nhapTrongKy = nhapTrongKy;
        this.xuatTrongKy = xuatTrongKy;
        this.tonCuoiKy = tonCuoiKy;
    }


    public String getMaThuoc() { return maThuoc; }
    public void setMaThuoc(String maThuoc) { this.maThuoc = maThuoc; }
    public String getTenThuoc() { return tenThuoc; }
    public void setTenThuoc(String tenThuoc) { this.tenThuoc = tenThuoc; }
    public String getDvt() { return dvt; }
    public void setDvt(String dvt) { this.dvt = dvt; }
    public int getTonDauKy() { return tonDauKy; }
    public void setTonDauKy(int tonDauKy) { this.tonDauKy = tonDauKy; }
    public int getNhapTrongKy() { return nhapTrongKy; }
    public void setNhapTrongKy(int nhapTrongKy) { this.nhapTrongKy = nhapTrongKy; }
    public int getXuatTrongKy() { return xuatTrongKy; }
    public void setXuatTrongKy(int xuatTrongKy) { this.xuatTrongKy = xuatTrongKy; }
    public int getTonCuoiKy() { return tonCuoiKy; }
    public void setTonCuoiKy(int tonCuoiKy) { this.tonCuoiKy = tonCuoiKy; }
}
    
