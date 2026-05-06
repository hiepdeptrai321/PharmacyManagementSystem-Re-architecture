package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.PhieuNhapItemRequest;
import com.example.pharmacy.common.request.PhieuNhapRequest;

public interface PurchaseOrderRepository {
    void insertHeader(String maPhieuNhap, String employeeId, PhieuNhapRequest request);

    void insertDetail(String maPhieuNhap, String maLo, PhieuNhapItemRequest item);

    void insertLot(String maPhieuNhap, String maLo, PhieuNhapItemRequest item);

    void updatePricing(String maThuoc, String maDvt, PhieuNhapItemRequest item);
}
