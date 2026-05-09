package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.CaiDatDto;

import java.util.List;

public interface CaiDatService {
    List<CaiDatDto> findAll();

    boolean update(CaiDatDto caiDat);
}
