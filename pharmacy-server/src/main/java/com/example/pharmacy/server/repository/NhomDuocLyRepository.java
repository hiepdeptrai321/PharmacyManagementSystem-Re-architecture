package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.NhomDuocLyEntity;

import java.util.List;
import java.util.Optional;

public interface NhomDuocLyRepository {
    List<NhomDuocLyEntity> findAll();

    Optional<NhomDuocLyEntity> findById(String maNhomDuocLy);

    NhomDuocLyEntity save(NhomDuocLyEntity entity);

    NhomDuocLyEntity update(NhomDuocLyEntity entity);

    boolean deleteById(String maNhomDuocLy);

    List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy);
}
