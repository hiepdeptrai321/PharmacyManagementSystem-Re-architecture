package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.CreateThuocRequest;

public interface MedicineCatalogRepository {
    void insertMedicine(String maThuoc, CreateThuocRequest request);

    void insertBaseUnit(String maThuoc, CreateThuocRequest request);
}
