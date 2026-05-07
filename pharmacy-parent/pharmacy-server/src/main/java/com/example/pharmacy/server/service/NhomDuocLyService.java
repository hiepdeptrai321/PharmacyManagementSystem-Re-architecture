package com.example.pharmacy.server.service;

import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;

import java.util.List;

public interface NhomDuocLyService {
    List<NhomDuocLy> findAll();

    NhomDuocLy findById(String maNhomDuocLy);

    String generateNewMaNhomDuocLy();

    boolean create(NhomDuocLy nhomDuocLy);

    boolean update(NhomDuocLy nhomDuocLy);

    boolean deleteById(String maNhomDuocLy);

    List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy);
}
