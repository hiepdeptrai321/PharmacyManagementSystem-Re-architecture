package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.NhaCungCapDto;

import java.util.List;

public interface NhaCungCapClientService {
    List<NhaCungCapDto> findAll();

    NhaCungCapDto findById(String maNhaCungCap);

    String generateNewMaNCC();

    boolean create(NhaCungCapDto nhaCungCap);

    boolean update(NhaCungCapDto nhaCungCap);

    boolean deleteById(String maNhaCungCap);
}
