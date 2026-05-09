package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.ThongSoUngDungEntity;

import java.util.List;

public interface CaiDatRepository {
    List<ThongSoUngDungEntity> findAll();

    boolean update(ThongSoUngDungEntity entity);
}
