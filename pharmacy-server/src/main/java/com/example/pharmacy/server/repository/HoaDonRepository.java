package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.HoaDon;

import java.sql.Date;
import java.util.List;

public interface HoaDonRepository {
    record UnitConversion(String maDvt, double heSoQuyDoi, boolean donViCoBan) {
    }

    record LotStock(String maLo, int soLuongTon, int soLuongDat, int soLuongGiu, Date hsd) {
    }

    void insertHeader(String maHoaDon, String employeeId, CreateHoaDonRequest request);

    void insertDetail(String maHoaDon, String maLo, String maDvt, int soLuong, double donGia, double giamGia);

    UnitConversion findUnitConversion(String maThuoc, String maDvt);

    List<LotStock> findLotsForUpdate(String maThuoc);

    boolean updateLotAfterSale(String maLo, int soLuongTonGiam, int reservedGiam);

    void updatePreorderStatus(String maPhieuDat, int status);

    List<HoaDon> findAll();

    HoaDon findById(String maHoaDon);

    List<ChiTietHoaDon> findDetailsByMaHD(String maHoaDon);
}
