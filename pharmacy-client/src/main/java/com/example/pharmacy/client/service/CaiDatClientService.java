package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.CaiDat;

import java.util.List;

public interface CaiDatClientService {
    List<CaiDat> findAll();

    boolean update(CaiDat caiDat);
}
