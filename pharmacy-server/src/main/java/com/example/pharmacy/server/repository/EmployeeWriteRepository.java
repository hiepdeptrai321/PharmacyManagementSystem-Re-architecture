package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.request.CreateEmployeeRequest;

public interface EmployeeWriteRepository {
    boolean existsByUsername(String username);

    void insert(String maNhanVien, CreateEmployeeRequest request);
}
