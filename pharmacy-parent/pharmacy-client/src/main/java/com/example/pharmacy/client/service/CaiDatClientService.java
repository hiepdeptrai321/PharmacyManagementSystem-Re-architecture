package com.example.pharmacy.client.service;

import com.example.pharmacymanagementsystem_qlht.model.CaiDat;

import java.util.List;

public interface CaiDatClientService {
    List<CaiDat> findAll();

    boolean update(CaiDat caiDat);
}
