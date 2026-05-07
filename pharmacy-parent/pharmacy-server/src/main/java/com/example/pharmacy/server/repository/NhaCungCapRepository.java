package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.NhaCungCapEntity;

import java.util.List;
import java.util.Optional;

public interface NhaCungCapRepository {
    List<NhaCungCapEntity> findAll();

    Optional<NhaCungCapEntity> findById(String maNhaCungCap);

    NhaCungCapEntity save(NhaCungCapEntity entity);

    NhaCungCapEntity update(NhaCungCapEntity entity);

    boolean deleteById(String maNhaCungCap);
}
