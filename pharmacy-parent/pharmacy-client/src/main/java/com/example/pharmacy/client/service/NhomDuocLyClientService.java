package com.example.pharmacy.client.service;

import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;

import java.util.List;

public interface NhomDuocLyClientService {
    List<NhomDuocLy> findAll();

    NhomDuocLy findById(String maNhomDuocLy);

    String generateNewMaNhomDuocLy();

    boolean create(NhomDuocLy nhomDuocLy);

    boolean update(NhomDuocLy nhomDuocLy);

    boolean deleteById(String maNhomDuocLy);

    List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy);
}
