package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.NhaCungCapDto;

import java.util.List;

public interface NhaCungCapService {
    List<NhaCungCapDto> findAll();

    NhaCungCapDto findById(String maNhaCungCap);

    String generateNewMaNCC();

    boolean create(NhaCungCapDto nhaCungCap);

    boolean update(NhaCungCapDto nhaCungCap);

    boolean deleteById(String maNhaCungCap);
}
