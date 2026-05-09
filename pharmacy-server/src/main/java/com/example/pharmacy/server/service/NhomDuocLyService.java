package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.NhomDuocLyDto;

import java.util.List;

public interface NhomDuocLyService {
    List<NhomDuocLyDto> findAll();

    NhomDuocLyDto findById(String maNhomDuocLy);

    String generateNewMaNhomDuocLy();

    boolean create(NhomDuocLyDto nhomDuocLy);

    boolean update(NhomDuocLyDto nhomDuocLy);

    boolean deleteById(String maNhomDuocLy);

    List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy);
}
