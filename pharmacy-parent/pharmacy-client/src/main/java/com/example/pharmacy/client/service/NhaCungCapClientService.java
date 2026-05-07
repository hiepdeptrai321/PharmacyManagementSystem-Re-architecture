package com.example.pharmacy.client.service;

import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;

import java.util.List;

public interface NhaCungCapClientService {
    List<NhaCungCap> findAll();

    NhaCungCap findById(String maNhaCungCap);

    String generateNewMaNCC();

    boolean create(NhaCungCap nhaCungCap);

    boolean update(NhaCungCap nhaCungCap);

    boolean deleteById(String maNhaCungCap);
}
