package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.CreatePhieuDoiItemRequest;
import com.example.pharmacy.common.request.CreatePhieuDoiRequest;
import com.example.pharmacy.common.request.CreatePhieuTraItemRequest;
import com.example.pharmacy.common.request.CreatePhieuTraRequest;
import com.example.pharmacy.common.model.ChiTietHoaDonDto;
import com.example.pharmacy.common.model.ChiTietPhieuDoiHangDto;
import com.example.pharmacy.common.model.ChiTietPhieuTraHangDto;
import com.example.pharmacy.common.model.HoaDonDto;
import com.example.pharmacy.common.model.PhieuDoiHangDto;
import com.example.pharmacy.common.model.PhieuTraHangDto;

import java.sql.Date;
import java.util.List;

public interface DoiTraRepository {
    record UnitConversion(String maDvt, double heSoQuyDoi, boolean donViCoBan) {
    }

    record LotStock(String maLo, int soLuongTon, Date hsd) {
    }

    HoaDonDto findHoaDonGoc(String maHoaDon);

    List<ChiTietHoaDonDto> findHoaDonDetails(String maHoaDon);

    void attachKhachHangToHoaDon(String maHoaDon, String maKhachHang);

    int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh);

    int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh);

    UnitConversion findUnitConversion(String maThuoc, String maDvt);

    List<LotStock> findLotsForUpdate(String maThuoc);

    boolean addStock(String maLoHang, int soLuongTonTang);

    boolean deductStock(String maLoHang, int soLuongTonGiam);

    void insertPhieuDoiHeader(String maPhieuDoi, CreatePhieuDoiRequest request, String maNhanVien);

    void insertPhieuDoiDetail(String maPhieuDoi, String maLoHangMoi, CreatePhieuDoiItemRequest item, int soLuongDisplay);

    void insertPhieuTraHeader(String maPhieuTra, CreatePhieuTraRequest request, String maNhanVien);

    void insertPhieuTraDetail(String maPhieuTra, CreatePhieuTraItemRequest item, double donGia, double giamGia);

    List<PhieuDoiHangDto> findAllPhieuDoi();

    PhieuDoiHangDto findPhieuDoiById(String maPhieuDoi);

    List<ChiTietPhieuDoiHangDto> findChiTietPhieuDoiByMaPD(String maPhieuDoi);

    List<PhieuTraHangDto> findAllPhieuTra();

    PhieuTraHangDto findPhieuTraById(String maPhieuTra);

    List<ChiTietPhieuTraHangDto> findChiTietPhieuTraByMaPT(String maPhieuTra);
}
