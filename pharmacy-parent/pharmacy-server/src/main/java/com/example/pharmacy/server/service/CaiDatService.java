package com.example.pharmacy.server.service;

import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.util.List;

public interface CaiDatService {
    List<CaiDat> findAll();

    boolean update(CaiDat caiDat);
}
