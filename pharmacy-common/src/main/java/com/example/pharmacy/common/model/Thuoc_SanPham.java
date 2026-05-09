package com.example.pharmacy.common.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Thuoc_SanPham implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String maThuoc;
    private String tenThuoc;
    private float hamLuong;
    private String donViHamLuong;
    private String duongDung;
    private String quyCachDongGoi;
    private String SDK_GPNK;
    private String hangSX;
    private String nuocSX;
    private NhomDuocLy nhomDuocLy;
    private LoaiHang loaiHang;
    private byte[] hinhAnh;
    private KeHang vitri;
    private List<ChiTietDonViTinh> dsCTDVT = new ArrayList<>();
    private List<Thuoc_SP_TheoLo> dsTS_TheoLo = new ArrayList<>();
    private ChiTietDonViTinh dvcb;
    private boolean ETC;

    public Thuoc_SanPham(){
    }

    public Thuoc_SanPham(String maThuoc, String tenThuoc, float hamLuong, String donViHamLuong, String duongDung, String quyCachDongGoi, String SDK_GPNK, String hangSX, String nuocSX, NhomDuocLy nhomDuocLy, LoaiHang loaiHang, byte[] hinhAnh, KeHang vitri, boolean ETC) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.hamLuong = hamLuong;
        this.donViHamLuong = donViHamLuong;
        this.duongDung = duongDung;
        this.quyCachDongGoi = quyCachDongGoi;
        this.SDK_GPNK = SDK_GPNK;
        this.hangSX = hangSX;
        this.nuocSX = nuocSX;
        this.nhomDuocLy = nhomDuocLy;
        this.loaiHang = loaiHang;
        this.hinhAnh = hinhAnh;
        this.vitri = vitri;
        this.ETC = ETC;
    }

    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public float getHamLuong() {
        return hamLuong;
    }

    public void setHamLuong(float hamLuong) {
        this.hamLuong = hamLuong;
    }

    public String getDonViHamLuong() {
        return donViHamLuong;
    }

    public void setDonViHamLuong(String donViHamLuong) {
        this.donViHamLuong = donViHamLuong;
    }

    public String getDuongDung() {
        return duongDung;
    }

    public void setDuongDung(String duongDung) {
        this.duongDung = duongDung;
    }

    public String getQuyCachDongGoi() {
        return quyCachDongGoi;
    }

    public void setQuyCachDongGoi(String quyCachDongGoi) {
        this.quyCachDongGoi = quyCachDongGoi;
    }

    public String getSDK_GPNK() {
        return SDK_GPNK;
    }

    public void setSDK_GPNK(String SDK_GPNK) {
        this.SDK_GPNK = SDK_GPNK;
    }

    public String getHangSX() {
        return hangSX;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }

    public String getNuocSX() {
        return nuocSX;
    }

    public void setNuocSX(String nuocSX) {
        this.nuocSX = nuocSX;
    }

    public NhomDuocLy getNhomDuocLy() {
        return nhomDuocLy;
    }

    public void setNhomDuocLy(NhomDuocLy nhomDuocLy) {
        this.nhomDuocLy = nhomDuocLy;
    }

    public LoaiHang getLoaiHang() {
        return loaiHang;
    }

    public void setLoaiHang(LoaiHang loaiHang) {
        this.loaiHang = loaiHang;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }
    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public KeHang getVitri() {
        return vitri;
    }

    public void setVitri(KeHang vitri) {
        this.vitri = vitri;
    }

    public List<Thuoc_SP_TheoLo> getDsTS_TheoLo() {
        return dsTS_TheoLo;
    }
    public void setDsTS_TheoLo(List<Thuoc_SP_TheoLo> dsTS_TheoLo) {
        this.dsTS_TheoLo = dsTS_TheoLo;
    }

    public List<ChiTietDonViTinh> getDsCTDVT() {
        return dsCTDVT;
    }

    public void setDsCTDVT(List<ChiTietDonViTinh> dsCTDVT) {
        this.dsCTDVT = dsCTDVT;
    }

    public ChiTietDonViTinh getDvcb() {
        return dvcb;
    }

    public void setDvcb(ChiTietDonViTinh dvcb) {
        this.dvcb = dvcb;
    }

    public boolean isETC() {
        return ETC;
    }

    public void setETC(boolean ETC) {
        this.ETC = ETC;
    }

    public Double getGiaNhapCoBan() {
        if (dsCTDVT != null) {
            for (ChiTietDonViTinh ct : dsCTDVT) {
                if (ct.isDonViCoBan()) return ct.getGiaNhap();
            }
        }
        return null;
    }

    public Double getGiaBanCoBan() {
        if (dsCTDVT != null) {
            for (ChiTietDonViTinh ct : dsCTDVT) {
                if (ct.isDonViCoBan()) return ct.getGiaBan();
            }
        }
        return null;
    }

    public String getTenDVTCoBan() {
        if (dsCTDVT != null) {
            for (ChiTietDonViTinh ct : dsCTDVT) {
                if (ct.isDonViCoBan()) return ct.getDvt().getTenDonViTinh();
            }
        }
        return null;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Thuoc_SanPham that = (Thuoc_SanPham) o;
        return Objects.equals(maThuoc, that.maThuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maThuoc);
    }

    @Override
    public String toString() {
        return "Thuoc_SanPham{" +
                "maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", hamLuong=" + hamLuong +
                ", donViHamLuong='" + donViHamLuong + '\'' +
                ", duongDung='" + duongDung + '\'' +
                ", quyCachDongGoi='" + quyCachDongGoi + '\'' +
                ", SDK_GPNK='" + SDK_GPNK + '\'' +
                ", hangSX='" + hangSX + '\'' +
                ", nuocSX='" + nuocSX + '\'' +
                ", nhomDuocLy=" + nhomDuocLy +
                ", loaiHang=" + loaiHang +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", vitri=" + vitri +
                '}';
    }

    public String getHamLuongDonVi(){
        if(donViHamLuong == null || donViHamLuong.isEmpty()){
            return String.valueOf(hamLuong);
        }
        return hamLuong + " " + donViHamLuong;
    }
}
