package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.CaiDatDto;

import java.util.List;

public interface CaiDatClientService {
    List<CaiDatDto> findAll();

    boolean update(CaiDatDto caiDat);
}
